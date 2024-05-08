<template>
  <div class="loginBody">
    <div class="loginDiv"  style="transform:translateY(-20px)">
        <div style="background-color: #000;">
         <div class="login-content" style="opacity: 0.8">
        <div class="login-title">注 册</div>
        <el-form
          :model="loginForm"
          label-width="100px"
          :rules="rules"
          ref="loginForm"
        >
          <el-form-item label="账 号" prop="no" >
            <el-input
            placeholder="请输入账号"
              style="width: 230px"
              type="text"
              v-model="loginForm.no"
              autocomplete="off"
              size="small"
            ></el-input>
          </el-form-item>
          <el-form-item label="密 码" prop="password">
            <el-input
            placeholder="请输入密码"
              style="width: 230px"
              type="password"
              v-model="loginForm.password"
              show-password
              autocomplete="off"
              size="small"
              @keyup.enter.native="confirm"
            ></el-input>
          </el-form-item>
           <el-form-item label="校验密码" prop="checkPassword">
            <el-input
            placeholder="请确认密码"
              style="width: 230px"
              type="password"
              v-model="loginForm.checkPassword"
              show-password
              autocomplete="off"
              size="small"
              @keyup.enter.native="confirm"
            ></el-input>
          </el-form-item>
           <el-form-item label="邮 箱" prop="email">
            <el-input
            placeholder="请输入邮箱"
              style="width: 230px"
              v-model="loginForm.email"
              autocomplete="off"
              size="small"
              @keyup.enter.native="confirm"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
            style="transform: translate(75px, 8px);height:39px;width: 230px;transform: translateX(0px)"
              type="primary"
              @click="confirm"
              :disabled="confirm_disabled"
              >确 定</el-button
            >
          </el-form-item>
        </el-form>
      </div>
        </div>
    </div>
    <router-view></router-view>
    </div>
   
</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      confirm_disabled: false,
      loginForm: {
        no: "",
        password: "",
        checkPassword: "",
        email:""

      },
      rules: {
        no: [{ required: true, message: "账号不能小于4位", trigger: "blur" }],
        password: [{ required: true, message: "密码不能小于8位", trigger: "blur" }],
        checkPassword: [{ required: true, message: "请确认密码", trigger: "blur" }],
        email: [{ required: true, message: "邮箱格式不正确", trigger: "blur" }],
      },
    };
  },
 
  methods: {
    confirm() {
      this.confirm_disabled = true;
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          //valid成功为true，失败为false
          this.$axios
            .post(this.$httpUrl + "/user/register", this.loginForm)
            .then((res) => res.data)
            .then((res) => {
              console.log(res);
              if (res.code == 0) {
                //存储
                // sessionStorage.setItem("CurUser", JSON.stringify(res.data.user));
                // console.log(res.data.menu);
                //跳转到登录页
                alert("注册成功！");
                this.$router.replace("/");
              } else if (res.code == 40000) {
                alert(res.description);
                this.confirm_disabled = false;
              } else {
                this.confirm_disabled = false;
                return false;
              }
            });
        } else {
          this.confirm_disabled = false;
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
.loginBody {
  position: absolute;
  width: 100%;
  height: 100%;
  /* background-image: linear-gradient(45deg, rgba(106, 255, 0, 0.09), rgba(116, 133, 255, 0.43));  */
   background-image: url('https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg');
  /* background-image: -webkit-linear-gradient(90deg,
    rgb(254, 205, 205),
    rgb(209, 240, 250)) */
    /* background-image: linear-gradient(180deg, rgba(255, 0, 221, 0.24), rgba(0, 217, 255, 0.63)); */
    /* background-image:linear-gradient(45deg, rgb(235 167 244 / 92%), rgb(168 255 242)); */

  /* background-image: linear-gradient(90deg, rgba(0, 255, 208, 0.4), rgba(255, 132, 0, 0.09));  */
  /* background-image: linear-gradient(45deg, rgb(235, 138, 249), rgb(122 255 235)); */
}
.loginDiv {
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -200px;
  margin-left: -250px;
  width: 450px;
  height: 450px;
  border-radius: 0px;
  background: #ffffff;
  box-shadow: 25px 25px 49px #ebebeb,
  -25px -25px 49px #efeeee;
}
  .login-title {
    margin: 30px 0;
    text-align: center;
    font-size: 19px;
    transform: translateX(10px)
  }

  .login-content {
    width: 400px;
    height: 250px;
    position: absolute;
    top: 25px;
    left: 25px;
  }

</style>
