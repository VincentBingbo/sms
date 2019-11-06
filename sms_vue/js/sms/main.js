//定义全局变量
const userId='';
const userName='';

//将变量暴露出去
export default{
    userId,
    userName,
    setUserId(newUserId) {
        this.userId = newUserId;
    }
}