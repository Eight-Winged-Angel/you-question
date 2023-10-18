<template>
  
  <el-menu default-active="1" class="el-menu-vertical-demo" background-color="#ffffff" text-color="#000000"
      active-text-color="#409eff" :router="true" :collapse="isCollapse" :width ="isCollapse ? '64px' : '200px' ">
      <div class="toggle-button" @click="toggleCollapse">||||</div>
    <el-submenu index="/student/course" class="subMenu">
      <template slot="title">
        <i class="el-icon-notebook-1 text-primary"></i>
        <span>我的课程</span>
      </template>
      <el-menu-item-group>
        <template slot="title">最近常用</template>
        <el-menu-item :index="'/student/course/' + courseInfo.courseId" class="menu-item-title"
         v-for="courseInfo in courseInfos" :key="courseInfo.courseId">
          <i class="el-icon-notebook-2 text-primary"></i>
          <span >{{ courseInfo.title }}</span>
        </el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="加入新课程">
        <el-menu-item class="menu-item-title">
          <el-form class="demo-form-inline">
            <el-form-item>
              <el-input placeholder="输入邀请码" 
              v-model=inviteCode 
              @blur="jumpToCourse"
              ></el-input>
              
            </el-form-item>
          </el-form>
        </el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-submenu index="/student/practice" class="subMenu" disabled>
      <template slot="title">
        <i class="el-icon-receiving text-primary"></i>
        <span>我的练习</span>
      </template>
    </el-submenu>
    <el-menu-item index="/student/account"  class="subMenu">
      <i class="el-icon-user text-primary"></i>
      <span slot="title">个人中心</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
  import axios from 'axios'
export default {
  data() {
    return {
      isCollapse:false,
      courseInfos: [{
        
        courseId:'',
        title: '',//title
        
        
      }],
      inviteCode:'1234567890ac',
    }
  }, watch: {
    $route: {
      handler() {
        
        // console.log("当前课程ida：",this.$route.params.id);
        var courseId = parseInt (this.$route.params.id)
        // console.log("当前课程ida：",courseId);
        this.$store.commit('GET_COURSEID',courseId)
      courseId = parseInt(this.$store.state.courseId)
        // console.log("当前课程idas：",window.sessionStorage.getItem('courseId'));

      },
      deep: true,
      immediate:true
    },
  },
  mounted:function(){
    console.log('token');
    // console.log(window.sessionStorage.getItem('token'), "here");
    axios.get('http://localhost:14514/student/course/list',{
          headers:{
            'token':this.$store.state.token
          },
          // method:'GET',
          // url:'http://localhost:14514/teacher/course/list',
          // data:window.sessionStorage.getItem("token")
          params: {
            page:1
          }
        }).then(response=>{
          console.log(response,"lllllllllllsssssw")
          this.courseInfos=response.data.data.records
          console.log(this.courseInfos)
          // window.sessionStorage.setItem("courseId", response.data.data);

  })

  },
  computed:{
    courseId(){
      return this.$store.state.courseId;
    }
  },
  methods:{
    toggleCollapse(){
      this.isCollapse = !this.isCollapse
    },

    jumpToCourse(){
      // console.log("aaaa");
      // console.log(this.inviteCode,"aaaa");

      //学生获取课程 用邀请码
      // console.log("oqqqqqqqqqqqqqqqqqqqq",this.inviteCode)
      axios.get('http://localhost:14514/student/course/invite',{
          headers:{
            'token':this.$store.state.token
          },

          params: {
            'inviteCode':this.inviteCode
          }
        }).then(response=>{
          console.log(response,"vvvvvvvvvvvvvvvvvvvvvvv")
          var id = response.data.data.courseId
          this.$router.push('/student/course/'+id);
          var courseId = parseInt(this.$store.state.courseId)
          axios.get('http://localhost:14514/student/course/verify',{
          headers:{
            'token':this.$store.state.token
          },
          params: {        
            'courseId':courseId  
          }
        }).then(response=>{
          console.log(response,"是是是是是是是是是是是是是是")
          this.joinCourse=response.data.data
          if(this.joinCourse == true){
            this.$store.commit('GET_SELECTED',this.joinCourse)
            console.log(this.$store.state.notSelected,this.joinCourse,"sssssssssssssssssssssxxxxxxxxxxxxxxxxx")
            this.quitDisabled = false;
            this.joinDisabled = true;}
          else{
            this.$store.commit('GET_SELECTED',this.joinCourse)
            console.log(this.$store.state.notSelected,this.joinCourse,"sssssssssssssssssssssxxxxxxxxxxxxxxxxx")
            this.quitDisabled = true;
            this.joinDisabled = false;}

          
          // this.joinnCourse=!this.joinCourse
          console.log( this.joinCourse,this.quitDisabled,joinDisabled,"是是是是是是是是s")
          
          // window.sessionStorage.setItem("courseId", response.data.data);

  })
          // this.courseInfos=response.data.data
          
          // window.sessionStorage.setItem("courseId", response.data.data);

  })
      

      // 未添加跳转到该课程路径
      // this.$router.push('/student/course/'+ '10000');
      }
  }
}
</script>

<style scoped>
.subMenu{
  margin-left:-15px}
.text-primary {
  color: #409eff;
}
.menu-item-title {
  padding-left: 40% !important;
  text-align: start;
  overflow:hidden;text-overflow:ellipsis;white-space:nowrap;
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