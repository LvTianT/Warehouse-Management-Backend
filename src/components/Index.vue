<template>
  <el-container style="height: 100%;width:100%">
    <el-aside
    class="aside"
      :width="aside_width"
      style="height:100vh;"
    >
      <Aside :isCollapse="isCollapse"></Aside>
    </el-aside>

    <el-container style="height: 100%;width: 100%">
     
      <el-header style="width:99.2%;text-align: right;height: 100%;
    border-radius: 10px; background-color: linear-gradient(rgb(205, 250, 254), rgb(251 255 253));">
     <div style="width: 100%;height:50px;">
        <Header @collapse="collapse" :icon="icon" style="transform: translateY(-12px);">
        </Header>
      </div>
      </el-header>

      <el-main style="height: 100%;">
        <!-- <Main></Main> -->
         <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Aside from "./Aside";
import Header from "./Header";
import Main from "./Main";

export default {
  name: "Index",
  components: { Aside, Header, Main },
  data() {
    return {
      isCollapse: false,
      aside_width: '200px',
      icon:"el-icon-s-fold"
   }
  },
  methods: {
    collapse() {
      console.log(1111);
      this.isCollapse = !this.isCollapse

      if (!this.isCollapse) {
      //展开
        this.aside_width = '200px'
        this.icon = "el-icon-s-fold"
      } else {
        //收起
        this.aside_width = '80px'
        this.icon = "el-icon-s-unfold"
      }
    },
  },
};
</script>

<style scoped>
.el-header {
  color: #f6f7f6;
  line-height: 60px;
  background-image: url('https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg');
  transform:translateY(-3px);
}

.el-main {
/* background-image: linear-gradient(10deg, rgb(255, 215, 212), rgb(220, 236, 252)); */
background-image: linear-gradient(5deg, rgb(239, 255, 250), rgb(229, 231, 255));
/* background-image: linear-gradient(5deg, rgb(255, 255, 239), rgb(229, 231, 255)); */
 /* background-color: #dbecdb; */
/* background-image: linear-gradient(180deg, rgba(123, 255, 0, 0.13), rgba(255, 0, 123, 0.06)); */
 /* 天蓝 */
/* background-image: linear-gradient(90deg, rgba(240, 184, 13, 0.09), rgba(133, 229, 253, 0.36)); */
/* 淡紫background-image: linear-gradient(45deg, rgba(106, 255, 0, 0.09), rgba(116, 133, 255, 0.43)); */
}
.el-aside {
   color:#e6ece6
}
.aside{
  border-right: 1px solid #dadfeb;
  /* background-image:linear-gradient(0deg,#ffffff, rgb(236 255 254)) */
}
.to{
  background: #e6ece6;
  margin-top: -4px;
}
</style>
