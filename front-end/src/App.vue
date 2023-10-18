<template>
  <div id="app" >
    <router-view ></router-view>
  </div>
</template>

<script>
export default { 
  name: 'App',
    provide () {    //父组件中通过provide来提供变量，在子组件中通过inject来注入变量。                                             
        return {

// <<<<<<< HEAD
            // reload: this.reload                                              
// =======
            // reload: this.reload
// >>>>>>> c6f52b1594525b313ef0122062f44e30043f81f8
        }
    },
    data() {
        return{
            // isRouterAlive: true                    //控制视图是否显示的变量
        }
    },
    // methods: {
    //     // reload () {
    //     //     this.isRouterAlive = false;            //先关闭，
    //     //     this.$nextTick(function () {
    //     //         this.isRouterAlive = true;         //再打开
    //         }) 
    //     }
    // },
}
</script>
<style>
* {
  padding: 0;
  margin: 0;
  font-family: PingFang SC, HarmonyOS_Regular, Helvetica Neue, Microsoft YaHei,
  sans-serif !important;
}

html,
body,
#app {
  height: 100%;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

li {
  list-style: none;
}

input {
  border: none;
  outline: none;
}

input:focus {
  border: none;
}

</style>
