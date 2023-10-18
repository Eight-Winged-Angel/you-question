<template>
  <el-container >
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
    <el-col :span="11" >
      <el-row style="font-size:30px;color:black" class="info">{{courseInfo.title}}</el-row>
      <el-row :gutter="20" style="background-color:;margin: auto;margin-bottom:2%;
          display: flex; align-items: center;" class="info">
        <el-col :span="4" style="background-color:;"></el-col>
        <el-col :span="8" style="justify-content: end;">{{courseInfo.schoolName}}</el-col>
        <el-divider direction="vertical"></el-divider>
        <el-col :span="8" style="justify-content: end;">{{courseInfo.teacherName}}</el-col>
        <el-col :span="4" style="background-color:;"></el-col>
      </el-row>
      <el-row :gutter="20" style="background-color:;margin: auto;margin-bottom:2%;
          display: flex; align-items: center;" class="info">
        <el-col :span="4" style="background-color:;"></el-col>
        <el-col :span="8" style="font-size:15px;color:#a5a5a5;background-color:;">{{courseInfo.inviteCode}}</el-col>
        <el-divider direction="vertical"></el-divider>
        <el-col :span="8" style="font-size:15px;color:#a5a5a5;background-color:;">{{courseInfo.total}}</el-col>
        <el-col :span="4" style="background-color:;"></el-col>
        </el-row>
      <el-row class="info" >
        <!-- 文本域 只读 不可更改size-->
      <el-input
      type="textarea"
      :autosize="{ minRows: 5, maxRows: 5}"
      show-word-limit
      maxlength="100"
      readonly

      
      resize=" none"

      placeholder="课程信息"
      v-model="courseInfo.content"
      clearable
      style="width:60%"
      >
    </el-input>
      </el-row>
      <el-row class="info">
        
        <el-button type="primary" @click="changingFormVisible=true" style="margin-right:2%" >修改信息</el-button>
        

        <!-- 以下是弹窗 -->
       
        

        <!-- 以上和创建新课程功能有关 -->
      <el-button type="danger" @click="dismissingFormVisible=true" style="margin-left:2%">解散课程</el-button>

      <el-dialog
            title="请输入课程信息"
            :visible.sync="changingFormVisible"
            width="30%"
            :before-close="handleClose">

            <el-form ref="changingFormRef" :label-position="labelPosition" label-width="80px" :model="courseInfo">
              <el-form-item label="课程名称" prop="title" :rules="[
                  { required: true, message: '课程名称不能为空', trigger: 'blur'}
                ]">
                <el-input v-model="courseInfo.title"></el-input>
              </el-form-item>
              <!-- <el-form-item label="开课学校" prop="school" :rules="[
                  { required: true, message: '开课学校不能为空', trigger: 'blur'}
                ]">
                <el-input v-model="courseInfo.school"></el-input>
              </el-form-item>
              <el-form-item label="开课教师" prop="teacher" :rules="[
                  { required: true, message: '开课教师不能为空', trigger: 'blur'}
                ]">
                <el-input v-model="courseInfo.teacher"></el-input>
              </el-form-item> -->
              <el-form-item label="课程简介" prop="content" :rules="[
                  { required: true, message: '课程简介不能为空', trigger: 'blur'}
                ]">
                <el-input
                  type="textarea"
                  :autosize="{ minRows: 3, maxRows: 7}"
                  show-word-limit
                  maxlength="100"
                  
                  placeholder="请输入课程简介"
                  v-model="courseInfo.content">
                </el-input>
              </el-form-item>
              <el-form-item label="课程简图" prop="picture" :rules="[
                  {},
                ]">
                <el-upload
                  class="upload"
                  action="https://jsonplaceholder.typicode.com/posts/"
                  :on-preview="handlePreview"
                  :on-remove="handleRemove"
                  :on-change="handleChange"
                  :file-list="fileList"
                  list-type="picture"
                  :limit="1"
                  :multiple="false"
                  style="margin-left: 0%"
                  disabled>
                  <el-button disabled size="small" type="primary" size:larger>点击上传</el-button>
                  <!-- <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button> -->
                  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>

                </el-upload>
              </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer" style="margin-top:0%;background-color:">
              <!-- <el-button @click="handleClose">取 消</el-button> -->
              <el-button type="primary" @click="confirmNew">确 定</el-button>
            </span>
          </el-dialog>
          <el-dialog
            title="真的要解散课程吗？"
            :visible.sync="dismissingFormVisible"
            width="30%"
            >

            
            <!-- <span slot="footer" class="dialog-footer" style="margin-top:0%;background-color:"> -->
              <!-- <el-button @click="handleClose">取 消</el-button> -->
              <el-button type="danger" @click="confirmDismiss">确 定</el-button>
            <!-- </span> -->
          </el-dialog>
      </el-row>
    </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
  import axios from 'axios'
export default {
  inject:['reload'],
  data() {
    return {

       //以下和创建新课程功能有关 
       fileList: [{name: 'coursePic.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
        labelPosition: 'right',
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
      changingFormVisible:false,
      dismissingFormVisible:false,
      //以上和创建新课程功能有关 
      fits: ['fill'],
      url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
      
     }
  },
  mounted(){

  },
  watch: {
    $route: {
      handler() {

        // console.log("当前课程id：",this.$route.params.id);
        
        var courseId = parseInt(this.$route.params.id)
        // window.sessionStorage.setItem('courseId',courseId)
        // courseId=  parseInt(window.sessionStorage.getItem('courseId'))
        this.$store.commit('GET_COURSEID',courseId)
      courseId = parseInt(this.$store.state.courseId)
      // console.log(courseId,"xxxxxxxxxxxxxxxxxxxxxxx")
        axios.get('http://localhost:14514/teacher/course/get',{
          headers:{
            'token':this.$store.state.token
          },
          params: {        
            'courseId':courseId  
          }
        }).then(response=>{
          // console.log(response,"vvvvvvvvvvvvvvvvvvvv")
          this.courseInfo=response.data.data
          // console.log(this.courseInfo)
          // window.sessionStorage.setItem("courseId", response.data.data);

  })

        //深度监听
        // this.$refs.breadcrumb.changeId(this.breadcrumbItems);
      },
      deep: true,
      immediate:true
    },
  },
  methods:{
    //以下和创建新课程功能有关 
    handleClose(done) {
        this.$confirm('确认取消修改课程信息？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
        },
       
      confirmNew(){
        this.$refs.changingFormRef.validate(valid => {
        if (!valid) return;
        this.changingFormVisible=false;
        //此处将课程信息传回后端
        // var courseId=  parseInt(window.sessionStorage.getItem('courseId'))
          var courseId = parseInt(this.$store.state.courseId)
        console.log("准备改课")
         axios.post('http://localhost:14514/teacher/course/update',{
          'title':this.courseInfo.title,
          'content':this.courseInfo.content,
          'courseId':courseId,
          
          
          },
          {
          headers:{
            'token':this.$store.state.token
          },
          
            
        }).then(response=>{
          // console.log("改课",response)
          location.reload();
          // console.log(response)
          // this.courseInfo=response.data.data
          // console.log(this.courseInfo)
          // window.sessionStorage.setItem("courseId", response.data.data);

  })
        this.$message({
          message: '修改课程信息成功！',
          type: 'success',
          center: true
        });
      })
      },
      confirmDismiss(){
        var courseId=  parseInt(this.$store.state.courseId)
        axios.get('http://localhost:14514/teacher/course/delete',{
          headers:{
            'token':this.$store.state.token
          },
          params: {
            'courseId':courseId,
            
          }
        }).then(response=>{
          console.log("解散成功")
          // this.reload();
// <<<<<<< HEAD
          location.reload();
// =======
          // location.reload();
// >>>>>>> c6f52b1594525b313ef0122062f44e30043f81f8
          // console.log(this.courseInfo)
          // window.sessionStorage.setItem("courseId", response.data.data);

  })
        this.dismissingFormVisible=false;
        this.$router.push('/teacher');
        this.$message({
          message: '解散课程成功！',
          type: 'success',
          center: true
        });
      
      },

      //以上和创建新课程功能有关 
  }
}
</script>

<style scoped>
.subTitle{
  margin-bottom: 0%;
  margin-top: 3%;
  font-size: 18px;
  cursor: pointer;
  color: #333;
  font-weight: bolder;}

.demonstration{
  margin-bottom: 2%;
  font-size: 18px;

  font-weight: bolder;
}

.info{
  margin-bottom: 3%;
}
</style>
