<template>
  <div>
  <el-menu
  class="menu"
    style="
      padding: 0;
      color: #000;
      height: 100vh;
      width: 197px;
      border: none;
      "
    default-active="/Home"
    :collapse="isCollapse"
    :collapse-transition="false"
    router
  >
    <el-menu-item index="/Home" active-text-color="#fff" style="width: 100%" >
      <i class="el-icon-s-home"></i>
      <span>首页</span>
    </el-menu-item>
    <el-menu-item
      class="m"
      :index="'/' + item.menuclick"
      v-for="(item, i) in menu"
      :key="i"
    >
      <i :class="item.menuicon"></i>
      <span slot="title" active-text-color="#fff" style="color: #000">{{
        item.menuname
      }}</span>
    </el-menu-item>
  </el-menu>
  </div>
</template>
<script>
export default {
  name: "Aside",
  data() {
    return {
      isCollapse: false,
    };
  },

  computed: {
    menu: {
      get() {
        return this.$store.state.menu;
      },
    },
  },
  props: {
    isCollapse: Boolean,
  },
};
</script>

<style scoped>
.el-menu {
  margin-left: 2px;
  position: relative;
     background-image: url('https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg')
}
.el-menu:hover {
  color: #fff;
   
}
.el-menu-item span {
  margin-left: 13px;

}

.el-menu-item:hover {
  color: #fff;
  border-right: 1px solid #91bade;
   
}
.el-menu-item i {
  margin-left: -8px;
}
element.style {
  margin-left: 5px;
}
.m:hover {
  border-right: 1px solid #7ebfff;
}

</style>
