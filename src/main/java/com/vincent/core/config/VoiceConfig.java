package com.vincent.core.config;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import javazoom.jl.player.Player;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.HashMap;

@Component
public class VoiceConfig {

    @Value("${voice.baidu.app_id}")
    private String APP_ID;
    @Value("${voice.baidu.app_key}")
    private String API_KEY;
    @Value("${voice.baidu.secret_key}")
    private String SECRET_KEY;

    public static final String filePath = "src/main/resources/static/mp3/";

    private static Player player;

    @Bean(value = "aipSpeech")
    public AipSpeech getAipSpeech() {
        // 初始化一个AipSpeech
        AipSpeech aipSpeech = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        aipSpeech.setConnectionTimeoutInMillis(2000);
        aipSpeech.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //aipSpeech.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //aipSpeech.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        // System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        return aipSpeech;
    }

    public void speak(String text, String fileName) throws IOException {
//        String text = "好的，已经帮您执行！";
        if (!getMP3ByText(text, filePath + fileName + ".mp3")) {
            System.out.println("转换失败");
        } else {
            File file = new File("F:\\work\\HBuilderProjects\\sms\\mp3\\"
                    + fileName + ".mp3");
            file.createNewFile();
            try (
                    FileInputStream oriFile = new FileInputStream(filePath + fileName);
                    FileOutputStream desFile = new FileOutputStream (file);
                    FileChannel oriChannel = oriFile.getChannel();
                    FileChannel desChannel = desFile.getChannel()) {
                oriChannel.transferTo(0, oriChannel.size(), desChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            playMP3(filePath + fileName);
        }
    }

    /**
     * 将文字转为MP3文件，需联网，依靠百度语音合成
     *
     * @param text
     * @return 是否成功
     */
    private boolean getMP3ByText(String text, String fileName) {
        // 设置可选参数
        HashMap<String, Object> options = new HashMap<>();
        // 语速，取值0-9，默认为5中语速
        options.put("spd", "5");
        // 音调，取值0-9，默认为5中语调
        options.put("pit", "5");
        // 音量，取值0-15，默认为5中音量
        options.put("vol", "5");
        // 发音人选择, 0为女声，1为男声，3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女
        options.put("per", "4");

        AipSpeech aipSpeech = getAipSpeech();
        // 调用接口
        // text 合成的文本，使用UTF-8编码。小于2048个中文字或者英文数字。（文本在百度服务器内转换为GBK后，长度必须小于4096字节）
        // lang 固定值zh。语言选择,目前只有中英文混合模式，填写固定值zh
        // ctp 客户端类型选择，web端填写固定值1
        TtsResponse res = aipSpeech.synthesis(text, "zh", 1, options);
        // 如果合成成功,下行数据为二进制语音文件，包含在data中。 如果合成出现错误，则会填充返回值到result中。
        // 若请求错误，服务器将返回的JSON文本包含以下参数：
        // error_code：错误码。
        // error_msg：错误描述信息，帮助理解和解决发生的错误。
        byte[] data = res.getData();
        JSONObject res1 = res.getResult();
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data, fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        if (res1 != null) {
            System.out.println(res1.toString(2));
        }

        return true;
    }

    private void playMP3(String fileName) {
        try {
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(fileName));
            // 需导入javazoom.jl.player.Player，下载地址http://www.javazoom.net/javalayer/sources/jlayer1.0.1.zip
            player = new Player(buffer);
            player.play();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String playerStatus() {
        if (player == null) {
            return "null";
        } else if (player.isComplete()) {
            return "played";
        } else {
            return "playing";
        }
    }
}
