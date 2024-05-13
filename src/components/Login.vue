<template>
  <div class="loginBody">
  
    <div class="loginDiv" >
        <div style="text-align:center;font-size:23px;transform:translate(10px,-70px);font-weight:300;" class="txt">仓 库 管 理 系 统 后 台</div>
        <div style="background-color: #000;">
         <div class="login-content" style="opacity: 0.8">
        <div class="login-title" style="transform:translate(0px,-10px);">登 录</div>
        <el-form
          :model="loginForm"
          label-width="100px"
          :rules="rules"
          ref="loginForm"
        >
          <el-form-item label="账 号" prop="no" style="transform:translate(-20px,-12px)">
            <el-input
            placeholder="请输入账号"
              style="width: 250px"
              type="text"
              v-model="loginForm.no"
              autocomplete="off"
              size="small"
            ></el-input>
          </el-form-item>
          <el-form-item label="密 码" prop="password" style="transform:translate(-20px,-14px)">
            <el-input
            placeholder="请输入密码"
              style="width: 250px"
              type="password"
              v-model="loginForm.password"
              show-password
              autocomplete="off"
              size="small"
              @keyup.enter.native="confirm"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
            style="transform: translate(-20px,-12px);height:39px;width: 250px;"
              type="primary"
              @click="confirm"
              :disabled="confirm_disabled"
              >确 定</el-button
            >
            <router-link to="/Register" class="register" style="transform: translate(5px,2px)">没有账号？去注册</router-link>
          </el-form-item>
        </el-form>
      </div>
        </div>
    </div>
    </div>
  
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      confirm_disabled: false,
      loginForm: {
        no: "",
        password: "",
      },
      rules: {
        no: [{ required: true, message: "请输入账号", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
    };
  },
 
  methods: {
    confirm() {
      this.confirm_disabled = true;
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          //valid成功为true，失败为false
          //去后台验证用户名密码
          this.$axios
            .post(this.$httpUrl + "/user/login", this.loginForm)
            .then((res) => res.data)
            .then((res) => {
              console.log(res);
              if (res.code == 200) {
                //存储
                sessionStorage.setItem("CurUser", JSON.stringify(res.data.user));

                console.log(res.data.menu);
                this.$store.commit("setMenu", res.data.menu)
                //跳转到主页
                this.$router.replace("/Home");
              } else {
                this.confirm_disabled = false;
                alert("校验失败，用户名或密码错误！");
                return false;
              }
            });
        } else {
          this.confirm_disabled = false;
          console.log("校验失败");
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
.txt{
  color:#000000;
}
.loginBody {
  position: absolute;
  width: 100%;
  height: 100%;
  /* background-image: -webkit-linear-gradient(90deg,
    rgb(254, 205, 205),
    rgb(209, 240, 250)) */
    /* background-image: linear-gradient(180deg, rgba(255, 0, 221, 0.24), rgba(0, 217, 255, 0.63)); */
    background-image:linear-gradient(45deg, rgb(235 167 244 / 92%), rgb(168 255 242));

  /* background-image: linear-gradient(90deg, rgba(0, 255, 208, 0.4), rgba(255, 132, 0, 0.09));   */
      /* background-image: linear-gradient(45deg, rgb(246 183 255), rgb(157 255 240)); */background-image: -moz-linear-gradient(90deg, rgb(235, 207, 252), rgb(254, 251, 205));

}
.loginDiv {
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -200px;
  margin-left: -250px;
  width: 450px;
  height: 330px;
  border-radius: 0px;
background: #ffffff;
box-shadow:  25px 25px 49px #ebebeb,
             -25px -25px 49px #efeeee;

/* border-radius: 42px;
background: #f7f7f7;
box-shadow:  12px 12px 24px #cbcbcb,
             -12px -12px 24px #ffffff;  */
  /* background: #fff; */
  /* border-radius: 5%; */
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
.register{
  text-decoration: none;
  font-size:5px;
color:#1e95fc;
float: right;
margin-top:-10px;
margin-right: 75px;
}

</style>
