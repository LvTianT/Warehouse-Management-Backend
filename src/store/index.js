import Vue from 'vue'
import Vuex from 'vuex'
import router, { resetRouter } from '../router'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)
function addNewRoute (menuList) {
  console.log(menuList)
  let routes = router.options.routes
  console.log(routes)
  routes.forEach(routeItem => {
    if (routeItem.path == "/Index") {
      menuList.forEach(menu => {
        let childRoute = {
          path: '/' + menu.menuclick,
          name: menu.menuname,
          meta: {
            title: menu.menuname
          },
          component: () => import('../components/' + menu.menucomponent)
          }
          routeItem.children.push(childRoute)
      })
    }
  })
    resetRouter()
    router.addRoutes(routes)
}

export default new Vuex.Store({
  state: {
    menu: []
  },
  mutations: {
    setMenu (state, menuList) {
      state.menu = menuList
      addNewRoute(menuList)
    }
  },
  getters: {
    getMenu (state) {
      return state.menu
    }
  },
  //vuex持久化存储，刷新后将sessionStorage中的数据重新返回到menu
  plugins:[createPersistedState()]
})
