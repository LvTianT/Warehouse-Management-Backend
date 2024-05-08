<template>
  <div id="app">
      <router-view/>
  </div>
</template>


<script>
export default {
  name: 'App',
  components: {},
  data() {
    return {
      //重新获取缓存的用户信息
      user:JSON.parse(sessionStorage.getItem('CurUser')),
    }
  },

  watch: {
    '$store.state.menu': {
      //监听menu状态变化，刷新后旧值为空，持久化处理后menu重新赋值发生变化，判断为空且用户存在且用户账号存在，调用store中的setMenu方法重置menu
      handler(val, old) {
        if (!old && this.user && this.user.no) {
          this.$store.commit("setMenu",val)
        }
      },
      immediate:true
    }
  },

  }
</script>
<style>
#app {
 height: 100%;
}


</style>
