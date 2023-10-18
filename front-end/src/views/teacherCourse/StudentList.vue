<template>
  <el-container style="margin-left:0% ;margin-top:2%">
    <!-- 面包屑导航区 -->
    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>第一</el-breadcrumb-item>
      <el-breadcrumb-item>第二</el-breadcrumb-item>
    </el-breadcrumb> -->

    <el-row>
    <!-- 卡片视图区域 -->
    <el-col :span="24" style="width:99%">
    <el-card class="box-card" style="margin-left:3%; margin-top:0%;width:99%">
      <!-- 搜索与添加区域 -->
      
       <el-row :gutter="20">
        <el-col :span="8" >
          <el-input placeholder="搜索学生" 
          v-model="queryInfo.query"
          clearable 
          @clear="getStudentList"
          >
           <!-- :v-model="queryInfo.query"  @clear="getCourseList" --> 

          <!-- ↑双向绑定到搜索关键字  -->

        <!-- 绑定单击事件 -->
          <el-button slot="append" icon="el-icon-search" 
          @click="getStudentList"
          ></el-button>
          <!-- @click="getCourseList" -->

          </el-input>
        </el-col>
        <!-- <el-col :span="4">
          <el-button type="primary">添加学生</el-button>

         </el-col> -->
      </el-row> 

      <!-- 课程（？）列表区域
      :data指定数据源 , -->
      <el-table :data="studentList" :row-class-name="tableRowClassName"  border stripe>
        

        <!-- 索引列 -->
        <el-table-column type="index"></el-table-column>
        <!-- 扩展列 -->
        <!-- <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="学生的各种信息">
                <span>{{ props.row.name }}</span>
              </el-form-item>
              <el-form-item label="学生的各种信息">
                <span>{{ props.row.shop }}</span>
              </el-form-item>
              <el-form-item label="学生的各种信息">
                <span>{{ props.row.id }}</span>
              </el-form-item>
              <el-form-item label="学生的各种信息">
                <span>{{ props.row.shopId }}</span>
              </el-form-item>
              <el-form-item label="学生的各种信息">
                <span>{{ props.row.category }}</span>
              </el-form-item>
              <el-form-item label="学生的各种信息">
                <span>{{ props.row.address }}</span>
              </el-form-item>
              <el-form-item label="学生的各种信息">
                <span>{{ props.row.desc }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column> -->
        <!-- 正常的各个列 -->
        <el-table-column label="学生姓名" prop="name"></el-table-column>
        <el-table-column label="学生ID" prop="userId"></el-table-column>
        <el-table-column label="学生学号" prop="studentId"></el-table-column>
        <el-table-column label="所在学校" prop="school"></el-table-column>
        <el-table-column label="操作"  width=120%>

          <!-- slot-scope="scope" -->
          
            <!-- 右侧 操作 栏下按钮 -->
            <!-- <el-button type="primary" icon="el-icon-edit" size="mini"></el-button>

            <el-tooltip effect="dark" :enterable="false" 
            content="提示按钮功能" placement="top">
             
            
            <el-button type="warning" icon="el-icon-setting" size="mini"></el-button>
          </el-tooltip> -->
          <el-tooltip effect="dark" :enterable="false" 
            content="移除学生" placement="top">
            <el-button type="danger" icon="el-icon-delete" size="mini"></el-button>
          </el-tooltip>
        </el-table-column>
       
      </el-table>

      <!-- 分页、页面跳转、页脚（？）区域 -->
      <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="this.queryInfo.pagenum"
      :page-sizes="[10]"
      :page-size='10'
      layout="total, sizes, prev, pager, next, jumper"
      :page-count="this.total"
      style="width:90%">
      <!-- 需要修改total，改为实际拥有的信息条数！！ -->
    </el-pagination>
    <!-- :total="total" -->

    </el-card>
  </el-col>
  </el-row>
    </el-container>
  </template>


<script>
  import axios from 'axios'
  export default {
    data() {
      return { 
        queryInfo:{
          query:'',//查询参数
          pagenum:1,//当前页码
          pagesize:10,//每页显示条数
          courseId:'',
          page:'',
          keyWord:''
        },
        studentList:[{
          name: '意义',
          userId: "123",
          studentId:"321",
          school: '嗷嗷',
        },
        {
          name: '啊',
          userId: "987",
          studentId:"789",
          school: '星星',
        },],
        total:0,
      }
    },
    watch: {
    $route:{
      handler() {

    var courseId=  parseInt(this.$route.params.id);
    // window.sessionStorage.setItem('courseId',courseId);
        this.$store.commit('GET_COURSEID',courseId)
    
      //api获取学生列表
      // courseId = parseInt(window.sessionStorage.getItem('courseId'));
      var courseId = parseInt(this.$store.state.courseId)
      // console.log("mmmmmmmmmmmmmmmmmmmmmmmmmmmmm",courseId);
      axios.get('http://localhost:14514/teacher/course/student/list',{
          
        headers:{
            'token':this.$store.state.token
          },
          params:{
            'courseId':courseId,
            page:this.queryInfo.pagenum,
            keyWord:this.queryInfo.query,
          }
          
        }).then(response=>{
          console.log(response,"pppppppppppppppppppppp")
          this.studentList=response.data.data.records
          this.total = response.data.data.pages
          // console.log(this.courseInfo)
          // window.sessionStorage.setItem("courseId", response.data.data);

  })
},
      deep: true,
      immediate:true
}
  },
    methods:{
      tableRowClassName({row, rowIndex}) {
        if (rowIndex ===1) {
          return 'warning-row';
        } else {
          return 'success-row';
        }
        return '';
      },
      getStudentList(){
        // 第一个参数'请求地址'
        console.log('在青丘了');
        // const{data:res}=  this.$http.get('studentList',{
        //   params:this.queryInfo
        // })
        // if(res.meta.status !== 200) 
        //   return this.$message.error('获取学生列表失败！')
        // this.studentList = res.data.studentList//学生信息
        // this.total = res.data.total//总页数
       var courseId = parseInt(this.$store.state.courseId)
      // console.log("mmmmmmmmmmmmmmmmmmmmmmmmmmmmm",courseId);
      axios.get('http://localhost:14514/teacher/course/student/list',{
        
          headers:{
            'token':this.$store.state.token},
            params:{
            'courseId':courseId,
            page:this.queryInfo.pagenum,
            keyWord:this.queryInfo.query,
          },
          
        }).then(response=>{
          console.log(response,"pppppppppppppqqqqqqqqqpp",this.queryInfo.query,this.queryInfo.pagenum,courseId)
          this.studentList=response.data.data.records
          this.total = response.data.data.pages
          // console.log(this.courseInfo)
          // window.sessionStorage.setItem("courseId", response.data.data);

  })

      },
      // 监听pagesize改变的事件
      handleSizeChange(newSize){
        this.queryInfo.pagesize = newSize
        console.log(newSize,"wassdwasdawd调用getstudentlist")
        this.getStudentList();

      },
      // 监听Current页码改变的事件
      handleCurrentChange(newPage){
        this.queryInfo.pagenum = newPage
        console.log(newPage,this.queryInfo.pagenum,"w调用getstudentlist")
        this.getStudentList();
      },


      // getcourseList(){
        // this.$http.get('')
      // },
      currentPage4(){
        
      }
    }
  }
</script>



<style scoped>

  .el-breadcrumb{
    margin-top:2%;
    margin-left: 2%;
    margin-bottom: 2%;
    font-size: 18px;
  }
  .el-card{
    /* 卡片式图的阴影 */
    box-shadow:0 1px 1px rgba(0,0,0,0.15) !important;
  }
  
  .el-table{
    margin-top: 2%;
  }

  /* 不知道为啥没生效 */
  .el-table .warning-row {
      background-color: rgb(242, 220, 179) !important;
    }
  
    .el-table .success-row {
      background-color: #d1edc3 !important;
    }

    .el-pagination{
margin-top: 2%;
    }
    .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
  </style>


  