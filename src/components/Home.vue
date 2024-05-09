<template>
  <div class="hea" >
    <div
      style="
        font-weight: normal;
        text-align: center;
        height: 100%;
        margin-bottom: 500px;
        padding:20px;
        margin-top: 10px;
        margin-left: 10px;
      "
      class="home"
    >
      <el-main style="height: 100%;">
        <el-descriptions
          title="个人信息"
          :column="2"
          size="40"
          border
          style="font-size: 10px; border: none"
        >
          <el-descriptions-item class="item">
            <template slot="label" class="cell">
              <i class="el-icon-s-custom"></i>
              账号
            </template>
            {{ user.no }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              电话
            </template>
            {{ user.phone }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-location-outline"></i>
              性别
            </template>
            <el-tag
              :type="user.sex === '1' ? 'primary' : 'danger'"
              disable-transitions
              ><i :class="user.sex == 1 ? 'el-icon-male' : 'el-icon-female'"></i
              >{{ user.sex == 1 ? "男" : "女" }}</el-tag
            >
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              角色
            </template>
            <el-tag type="success" disable-transitions>{{
              user.roleId == 0
                ? "高级管理员"
                : user.roleId == 1
                ? "管理员"
                : "用户"
            }}</el-tag>
          </el-descriptions-item>
        </el-descriptions>
        <DateUtils></DateUtils>
      </el-main>
    </div>

  <div
      class="card"
      v-if="display"
      style="margin-left: 900px; margin-top: 100px;transform:translate(-20px,-370px)"
  >
    <div class="header"></div>
    <div class="info">
      <p class="title">Welcome !</p>
      <p>Check your information if it has been updated.</p>
    </div>
    <div class="footer">
      <p class="tag" style="font-size: 15px; margin-left: 15px">confirm ?</p>
      <button type="button" class="action" @click="confirm">OK</button>
    </div>
  </div>
  </div>
</template>

<script>
import DateUtils from "./DateUtils";
export default {
  name: "Home",
  components: { DateUtils },
  data() {
    return {
      display: true,
      user: {},
    };
  },
  computed: {},
  methods: {
    init() {
      this.user = JSON.parse(sessionStorage.getItem("CurUser"));
    },
    confirm() {
      this.display = !this.display;
    },
  },
  created() {
    this.init();
  },
};
</script>

<style scoped>

.el-main {
  overflow: hidden;
  padding: 9px;
}
.el-descriptions {
  width: 90%;
  margin: 0 auto;
  text-align: center;
}
.home {
  /* border-radius: 50px;
background: #f7f7f7;
box-shadow:  20px 20px 17px #dff9f2,
             -20px -20px 17px #ffffff;
} */
  /* border-radius: 54px;
  background: linear-gradient(145deg, #fcf8f8, #ffffff);
  box-shadow: 20px 20px 0px #eef6f7, -20px -20px 0px #ffffff; */
  border-radius: 62px;
  box-shadow: inset 9px 9px 18px #dfe5e6, inset -9px -9px 18px #f0ffff;
  margin-right: 15px;
  background-color: #e7e4ff;
}

.card {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border-radius: 0.75rem;
  background-color: white;
  width: 300px;
  height: 370px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1),
    0 2px 4px -2px rgba(0, 0, 0, 0.1);
  background-image: url("https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg");
}

.header {
  position: relative;
  background-clip: border-box;
  margin-top: 1.5rem;
  margin-left: 1rem;
  margin-right: 1rem;
  border-radius: 0.75rem;
  background-color: rgb(220, 237, 251);
  box-shadow: 0 10px 15px -3px rgba(66, 106, 138, 0.4),
    0 4px 6px -4px rgba(153, 208, 252, 0.4);
  height: 14rem;
}

.info {
  border: none;
  padding: 1.5rem;
  text-align: center;
}

.title {
  color: rgb(80, 92, 98);
  letter-spacing: 0;
  line-height: 1.375;
  font-weight: 600;
  font-size: 1.25rem;
  margin-bottom: 0.5rem;
}

.footer {
  padding: 0.75rem;
  border: 1px solid rgb(236 239 241);
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: rgba(8, 90, 131, 0.082);
  /* background-image: url('https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg') */
}

.tag {
  font-weight: 300;
  font-size: 0.75rem;
  display: block;
}

.action {
  -webkit-user-select: none;
  -moz-user-select: none;
  user-select: none;
  border: none;
  outline: none;
  box-shadow: 0 4px 6px -1px rgba(184, 217, 245, 0.4),
    0 2px 4px -2px rgba(33, 150, 243, 0.4);
  color: rgb(255 255 255);
  text-transform: uppercase;
  font-weight: 700;
  font-size: 0.75rem;
  padding: 0.75rem 1.5rem;
  background-color: rgb(48, 158, 248);
  border-radius: 0.5rem;
}
.action:hover{
  background: #a9cbee;
}
.cell {
  border: 1px solid #3b3b3b;
  padding: 12px 10px;
  background-color: #ffffff;
}
</style>
