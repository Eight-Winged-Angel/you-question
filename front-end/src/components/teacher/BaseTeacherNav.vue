<template>

  <div style="">
    <el-menu default-active="1" class="el-menu-vertical-demo"
             background-color="#ffffff" text-color="#000000"
             active-text-color="#409eff" :router="true" :collapse="isCollapse"
             :width="isCollapse ? '64px' : '200px' "
    >
      <div class="toggle-button" @click="toggleCollapse">||||</div>
      <el-submenu index="/teacher" class="subMenu">
        <template slot="title">
          <i class="el-icon-notebook-1 text-primary"></i>
          <span>我的课程</span>
        </template>
        <el-menu-item-group>
          <template slot="title">最近常用</template>
          <el-menu-item :index="'/teacher/course/' + courseInfo.courseId" class="menu-item-title"
                        v-for="courseInfo in courseInfos" :key="courseInfo.courseId">
            <i class="el-icon-notebook-2 text-primary"></i>
            <span>{{ courseInfo.title }}</span>
          </el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="创建新课程">

          <!-- 以下和创建新课程功能有关 -->
          <el-menu-item class="menu-item-title">
            <i class="el-icon-circle-plus-outline text-primary"></i>
            <span @click="dialogVisible=true">创建新课程</span>

            <el-dialog
                title="请输入课程信息"
                :visible.sync="dialogVisible"
                width="30%"
                :before-close="handleClose">

              <el-form ref="formRef" :label-position="labelPosition" label-width="80px" :model="newCourse">
                <el-form-item label="课程名称" prop="title" :rules="[
                  { required: true, message: '课程名称不能为空', trigger: 'blur'}
                ]">
                  <el-input v-model="newCourse.title"></el-input>
                </el-form-item>
                <el-form-item label="开课学校" prop="schoolName" :rules="[
                  { required: true, message: '开课学校不能为空', trigger: 'blur'}
                ]">
                  <el-input v-model="newCourse.schoolName"></el-input>
                </el-form-item>
                <el-form-item label="开课教师" prop="teacherName" :rules="[
                  { required: true, message: '开课教师不能为空', trigger: 'blur'}
                ]">
                  <el-input v-model="newCourse.teacherName"></el-input>
                </el-form-item>
                <el-form-item label="课程简介" prop="content" :rules="[
                  { required: true, message: '课程简介不能为空', trigger: 'blur'}
                ]">
                  <el-input
                      type="textarea"
                      :autosize="{ minRows: 3, maxRows: 7}"
                      show-word-limit
                      maxlength="100"

                      placeholder="请输入课程简介"
                      v-model="newCourse.content">
                  </el-input>
                </el-form-item>
                <el-form-item label="课程简图" prop="picture" :rules="[
                  {},
                ]">
                  <el-upload
                      class="upload"
                      action="https://jsonplaceholder.typicode.com/posts/"
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
          </el-menu-item>

          <!-- 以上和创建新课程功能有关 -->
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="/teacher/bank" class="subMenu">
        <template slot="title">
          <i class="el-icon-receiving text-primary"></i>
          <span>我的题库</span>
        </template>
        <el-menu-item class="menu-item-title" index="/teacher/bank/question">
          <i class="el-icon-edit text-primary"></i>
          <span>题目</span>
        </el-menu-item>
        <el-menu-item class="menu-item-title" index="/teacher/bank/practice">
          <i class="el-icon-edit-outline text-primary"></i>
          <span>练习</span>
        </el-menu-item>
      </el-submenu>
      <el-menu-item index="/teacher/account" class="subMenu">
        <i class="el-icon-user text-primary"></i>
        <span slot="title">个人中心</span>
      </el-menu-item>
    </el-menu>

  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      //以下和创建新课程功能有关 
      fileList: [{
        name: 'coursePic.jpeg',
        url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
      }],
      labelPosition: 'right',
      courseInfos: [{
        courseCover: '',
        courseId: '',
        title: '',//title
        teacherId: '',
        content: ''
      }],
      newCourse: {
        courseCover: '',
        courseId: '',
        title: '',//title
        teacherId: '',
        content: ''
      },
      dialogVisible: false,
      //以上和创建新课程功能有关 
      isCollapse: false
    }
  },
  watch: {
    $route: {
      handler() {

        // console.log("当前课程ida：",this.$route.params.id);
        var courseId = parseInt(this.$route.params.id)
        // console.log("当前课程ida：",courseId);
        // window.sessionStorage.setItem('courseId', courseId);
        this.$store.commit('GET_COURSEID',courseId)
        console.log("aaaaaaaaaaaaaaaaaaaaa",courseId,this.$store.state.courseId)
        // console.log("当前课程idas：",window.sessionStorage.getItem('courseId'));

      },
      deep: true,
      immediate: true
    }
  },
  // computed:{
  //   courseId(){
  //     return this.$store.state.courseId;
  //   }
  // },

  mounted: function () {
    console.log('token');
    console.log(this.$store.state.token, 'here');
    axios.get('http://localhost:14514/teacher/course/list', {
      headers: {
        // 'token': window.sessionStorage.getItem('token')
        'token':this.$store.state.token
      },
      // method:'GET',
      // url:'http://localhost:14514/teacher/course/list',
      // data:window.sessionStorage.getItem("token")
      params: {
        page: 1
      }
    }).then(response => {
      console.log(response,"lllllllllllsssssw")
      this.courseInfos = response.data.data.records
      console.log(this.courseInfos,"ttttttttttttttttttttttttt")

    })

  },
  methods: {
    toggleCollapse() {
      this.isCollapse = !this.isCollapse
    },

    //以下和创建新课程功能有关
    handleClose(done) {
      this.$confirm('确认取消创建课程？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    },

    confirmNew() {
      this.$refs.formRef.validate(valid => {
        if (!valid) return;
        this.dialogVisible = false;
        //api
        // var courseId = parseInt(window.sessionStorage.getItem('courseId'))

        var courseId = this.$store.state.courseId

        console.log(this.newCourse.title, this.newCourse.content, courseId, 'ooooooooooooooooo')
        axios.post('http://localhost:14514/teacher/course/add', {
              'title': this.newCourse.title,
              'content': this.newCourse.content,
              // 'courseId': 11000
            },
            {
              headers: {
                // 'token': window.sessionStorage.getItem('token')
                'token':this.$store.state.token
              }


            }).then(response => {
          // console.log('xxxxxxxxxxxxxxx', response.data)
          console.log("创建课程成功",response)
          // location.reload();
          // this.$router.push('/teacher');
          // console.log(response)
          // this.courseInfo=response.data.data
          // console.log(this.courseInfo)
          // window.sessionStorage.setItem("courseId", response.data.data);

        })


        this.$message({
          message: '创建课程成功！',
          type: 'success'
        });
      })
    }

    //以上和创建新课程功能有关

  }
}
</script>

<style scoped>
.subMenu {
  margin-left: -15px
}

.text-primary {
  color: #409eff;
}

.menu-item-title {
  padding-left: 40% !important;
  text-align: start;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.toggle-button {
  background-color: #4a5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  /* 文字居中 */
  text-align: center;
  letter-spacing: 0.2rem;
  /* 选中时鼠标指针变化为小手 */
  cursor: pointer;
}
</style>