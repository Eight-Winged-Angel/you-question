<template>
  <el-container>
    <!-- <el-header class="subTitle" >课程基本信息</el-header> -->
    <el-main style="background-color: #eaeaea">
      <el-row gutter="20" style="background-color: ;margin: auto;
          display: flex; align-items: center;">
          <el-col :span="0.5"></el-col>
        <el-col :span="12" style="background-color:;height:auto">
        <div class="demo-image" style="background-color:;margin-left:auto">
        <div class="block" v-for="fit in fits" :key="fit">
          <el-image
            style="width: 400px; height:250px;border-radius: 3px;"
            :src="url"
            :fit="fit"></el-image>
        </div>
      </div>
    </el-col>
        <el-col :span="11">
          <el-row style="font-size: 30px; color: black" class="info">{{
            courseInfo.title
          }}</el-row>
          <el-row
            :gutter="20"
            style="
              background-color: ;
              margin: auto;
              margin-bottom: 2%;
              display: flex;
              align-items: center;
            "
            class="info"
          >
            <el-col :span="4" style="background-color: "></el-col>
            <el-col :span="8" style="justify-content: end">{{
              courseInfo.schoolName
            }}</el-col>
            <el-divider direction="vertical"></el-divider>
            <el-col :span="8" style="justify-content: end">{{
              courseInfo.teacherName
            }}</el-col>
            <el-col :span="4" style="background-color: "></el-col>
          </el-row>
          <el-row
            :gutter="20"
            style="
              background-color: ;
              margin: auto;
              margin-bottom: 2%;
              display: flex;
              align-items: center;
            "
            class="info"
          >
            <el-col :span="4" style="background-color: "></el-col>
            <el-col
              :span="8"
              style="font-size: 15px; color: #a5a5a5; background-color: "
              >
              {{courseInfo.inviteCode}}
              </el-col
            >
            <el-divider direction="vertical"></el-divider>
            <el-col
              :span="8"
              style="font-size: 15px; color: #a5a5a5; background-color: "
              >{{courseInfo.total}}</el-col
            >
            <el-col :span="4" style="background-color: "></el-col>
          </el-row>
          <el-row class="info">
            <!-- 文本域 只读 不可更改size-->
            <el-input
              type="textarea"
              :autosize="{ minRows: 5, maxRows: 5 }"
              show-word-limit
              maxlength="100"
              readonly
              resize=" none"
              placeholder="课程信息"
              v-model="courseInfo.content"
              clearable
              style="width: 60%"
            >
            </el-input>
          </el-row>
          <el-row class="info">
            <!-- <div v-show="joinCourse"> -->
            <el-button
            @click="joinIt()"
            :disabled="joinDisabled"
              type="primary"
              
              >加入课程</el-button
            >
          <!-- </div> -->
            <!--  = false" -->
            <!-- :disabled="joinDisabled" -->
            <!-- <div  v-show="joinCourse"> -->
            <el-button
           
              type="danger"
              :disabled="quitDisabled"
              @click="quitCourse(courseInfo.courseId)"
              >退选课程</el-button
            >
          <!-- </div> -->
          </el-row>
        </el-col>
      </el-row>
      <el-row style="background-color: ; width: 1000px">
        <!-- <div class="demo-image">
        <div class="block" v-for="fit in fits" :key="fit">
          <el-image
            style="width: 400px; height:250px"
            :src="url"
            :fit="fit"></el-image>
        </div>
      </div> -->
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
  import {nanoid} from 'nanoid'
  import axios from 'axios'
export default {
  data() {
    return {
      joinCourse:true,
      joinnCourse:false,
      courseInfo: {
          courseId:'',
          title: 'JAVA EE程序设计实践',
          schoolName:'北京邮电大学',
          teacherName: '孙艺，孙溢',
          content: '这里是课程信息这里是课程信息这里是课程信息这里是课程信息这里是课程信息这里是课程信息',
          inviteCode:'',
          total:'',
          startTime:'',
          endTime:''
        },
      fits: ["fill"],
      url: "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100",
      quitDisabled: false,
      joinDisabled:false,
      // 未设置根据是否已加课调整是否禁用加入课程按钮
    };
  },watch: {
    joinCourse:{

    }
    ,
    $route: {
      handler() {
        //学生获取课程详细信息
        // console.log("id：",courseId);
        // console.log("当前课程id：",this.$route.params.id);
        var courseId = parseInt(this.$route.params.id)
        this.$store.commit('GET_COURSEID',courseId)
        courseId = parseInt(this.$store.state.courseId)
        axios.get('http://localhost:14514/student/course/get',{
          headers:{
            'token':this.$store.state.token
          },
          params: {        
            'courseId':courseId  
          }
        }).then(response=>{
          console.log(response,",,,,,,,,,,,,,,,,,,,,")
          this.courseInfo=response.data.data
          console.log(this.courseInfo)
          // window.sessionStorage.setItem("courseId", response.data.data);
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
          console.log( this.joinCourse,this.quitDisabled,"是是是是是是是是s")
          
          // window.sessionStorage.setItem("courseId", response.data.data);

  })

  })
 

      },
      deep: true,
      immediate:true
    },
  },
  created(){
    
    courseId = parseInt(this.$store.state.courseId)
    console.log(courseId,"cococococococococococoo")
    //获取学生是否已加入课程，返回到joinCourse中，布尔值
    

   
      


  },
  methods: {
    joinIt() {
      console.log("gggggggggggggggg")
      // this.$store.commit('GET_COURSEID',courseId)
      var courseId = parseInt(this.$store.state.courseId)
      axios.get('http://localhost:14514/student/course/add',{
            
          headers: {        
            'token':this.$store.state.token
          },
          params: {
            'courseId':courseId,
            
          }
        }).then(response=>{
          console.log("学生选课成功");
          // this.$router.push('/student');
          //location.reload();
          
          // window.sessionStorage.setItem("courseId", response.data.data);

  })
      this.$message({
        message: "已加入课程，快去学习吧！",
        type: "success",
        center: true
      });
    },
    //学生退课
    quitCourse(id) {
      if(confirm('确定退课吗？')){
      this.$router.push('/student');
      //将学生退课返回到后端
      //api
      
      var courseId = parseInt(this.$store.state.courseId)
      axios.get('http://localhost:14514/student/course/delete',{
          headers:{
            'token':this.$store.state.token
          },
          params: {        
            'courseId':courseId  
          }
        }).then(response=>{
          console.log("学生退课成功")
          // location.reload()
          // window.sessionStorage.setItem("courseId", response.data.data);

  })
      this.$message({
        message: "退课成功！",
        type: "success",
        center: true
      });
    }
    }
  },
};
</script>

<style scoped>
.subTitle {
  margin-bottom: 0%;
  margin-top: 3%;
  font-size: 18px;
  cursor: pointer;
  color: #333;
  font-weight: bolder;
}

.demonstration {
  margin-bottom: 2%;
  font-size: 18px;

  font-weight: bolder;
}

.info {
  margin-bottom: 3%;
}
</style>
