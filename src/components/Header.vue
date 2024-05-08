<template>
  <div style="display: flex; line-height: 60px; width: 100%" class="header">
    <div style="margin-top: 8px">
      <i
        :class="icon"
        style="font-size: 20px; color: rgb(115 193 243)"
        @click="collapse"
      ></i>
    </div>
    <div style="flex: 1; text-align: center">
      <div class="welcome">
        <span
          style="
          text-align: center;
            width: 70%;
            height: 70%;
            font-size: 24px;
            font-weight: 200;
            color: #000;
          "
          >智 能 仓 储 管 理 系 统</span
        >
      </div>
    </div>
    <el-dropdown>
      
      <span class="manager" >{{ user.no }}</span
      >
   <i class="el-icon-arrow-down" style="margin-left: 7px"></i>
      <el-dropdown-menu slot="dropdown" style="height:90px;">
           <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" style="transform: translate(30px,-3px);width:25px;height:25px;"></el-avatar>
        <el-dropdown-item @click.native="toUser" style="font-size: 10px;" >个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logOut" style="font-size: 10px;">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem("CurUser")),
    };
  },
  props: {
    icon: String,
  },
  methods: {
    toUser() {
      console.log("to_user");
      this.$router.push("/Home")
    },
    logOut() {
      console.log("logout");

      this.$confirm("您确定要退出登录吗?", "提示", {
        confirmButtonText: "确定", //确认按钮的文字显示
        type: "warning",
        center: true, //文字居中显示
      })
        .then(() => {
          this.$message({
            type: "success",
            message: "退出登录成功",
          });

          this.$router.push("/");
          sessionStorage.clear();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消退出登录",
          });
        });
    },
    collapse() {
      this.$emit("collapse");
    },
     created() {
    this.$router.push("/Home");
  },
  },
};
</script>

<style scoped>
.header{
  /* background-color: rgb(255, 255, 255); */
 background-color: linear-gradient(rgb(205, 250, 254), rgb(251 255 253));
 border-bottom:1px solid #e6e6e6;
}
.welcome {
  font-size: 34px;
  display: inline-block;
  font-weight: normal;
  color: #313434;
}

.manager {
  color: #050816c5;
  font-family: sans-serif;
  cursor: pointer;
}

.el-icon-arrow-down {
  color: #000;
}
</style>
