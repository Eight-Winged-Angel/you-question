<template>
  <!-- 不知道为啥，主页界面显示不出来，暂时新建了一个nav页面进行测试 -->
  <el-container>
  <el-aside :width ="isCollapse ? '64px' : '200px' ">
  <div class="toggle-button" @click="toggleCollapse">||||</div>
  <el-menu
    class="el-menu-vertical-demo"
    @open="handleOpen"
    @close="handleClose"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    :unique-opened="true"
    :collapse="isCollapse" 
    :collapse-transition="false"
    router :default-active="activePath">
    <!-- 一级菜单 -->
    <el-submenu :index="item.id + ''" v-for="item in menulist" :key="item.id">
      <!-- 一级菜单模板区 -->
      <template slot="title">
        <!-- 图标 -->
        <i class="el-icon-location"></i>
        <!-- 文本 -->
        <span>{{item.authName}}</span>
      </template>
      
      <el-menu-item :index="'/' + subItem.path +''" 
      v-for="subItem in item.children" 
      :key="subItem.id" @click="saveNavState('/' + subItem.path +'')"></el-menu-item>
      <!-- 二级菜单模板区 -->
      <template slot="title">
        <!-- 图标 -->
        <i class="el-icon-menu"></i>
        <!-- 文本 -->
        <span>{{item.authName}}</span>
      </template>

    </el-submenu>
  </el-menu>

      <!-- 
        分组与选项↓
        <el-menu-item-group>
        <template slot="title">分组一</template>
        <el-menu-item index="1-1">选项1</el-menu-item>
        <el-menu-item index="1-2">选项2</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="分组2">
        <el-menu-item index="1-3">选项3</el-menu-item>
      </el-menu-item-group>
      <el-submenu index="1-4">
        <template slot="title">选项4</template> -->
      <!-- </el-submenu> -->
    
  
    </el-aside>
  </el-container>
</template>

<script>
  export default {
    //发起请求获取左侧菜单数据，保存到menulist数组中，其中每一项是一级菜单，children中是二级菜单  课程p34
    data(){
      return{
        // 左侧菜单数据
        menulist:[],
        // 左侧导航栏不折叠
        isCollapse:false,
        activePath:''
      }
    },
    // 打开网页时立即获取所有的左侧菜单
    created(){
      this.getMenuList(),
      this.activePath = window.sessionStorage.getItem('activePath')
      
    },
    methods:{
      logout(){
        window.sessionStorage.clear()
        this.$router.push('/login')
      },
      async getMenuList(){
        // 获取左侧菜单
        const {data:res} = await this.$http.get('menus')
        console.log(res)
        // 如果获取数据失败，打印错误消息，消息来源是meta.msg
        // if(res.meta.status !== 200) return this.$message.error(res.meta.msg)
        // 将res.data也即左侧菜单数据挂到menulist
        this.menulist = res.data
      },
      // 点击按钮，切换菜单的折叠与展开
      toggleCollapse(){
        this.isCollapse = !this.isCollapse
      },
      // 保存链接（导航栏的某一项）的激活状态
      saveNavState(activePath){
        window.sessionStorage.setItem('activePath',activePath)
        this.activePath=activePath
      }
    }
  }
</script>
  
<style scoped>
  
  .el-menu{
      border-right: 0px
  }
  
  .toggle-button{
    background-color: #4a5064;
    font-size:10px;
    line-height: 24px;
    color:#fff;
    /* 文字居中 */
    text-align: center;
    letter-spacing: 0.2rem;
    /* 选中时鼠标指针变化为小手 */
    cursor:pointer;
  }
</style>
  