<template>
  <el-container  
  style="margin: auto;
          display: flex;
          justify-content: center;
          align-items:center;
          background-color: #eaeaea;
          ">

    <!-- <el-col style="margin-bottom:2.3%">课程公告</el-col> -->


    
    <div class="box" style="width:600px;margin-top:5.3%">
      <ul
        class="list"
        v-infinite-scroll="infiniteLoad"
        infinite-scroll-disabled="disabled"
        
      >
        <li v-for="notice in notices" :key="notice.id" :notice="notice" 
        class="list-item" style="margin-top:1.5%;background-color:;border-radius:4px">
        <!-- style=";background-color:#f6dcd7" -->
      <el-collapse style=";background-color:;border-radius:4px" >
        <el-collapse-item  name="2"  style="font-weight:normal;;">
          <template slot="title" >
            <el-row style="width:100%;margin: auto;
              display: flex;align-items: center;">
            <el-col :span="16" 
            class="noticeTitle title"
            style="background-color:" >
                &nbsp;&nbsp;
                {{notice.title}}
              </el-col>
            <el-col :span="8" 
            class="noticeDate"
            style="background-color:;">
                {{notice.updateTime.substring(0,10)}}
                &nbsp;&nbsp;</el-col>
          </el-row>
          </template>
          <el-row style="margin: auto;display: flex;align-items: center;justify-content: start;">
            <el-col :span="24" style="margin-top:4%;text-align: left;letter-spacing:normal; padding-left: 20px;padding-right: 20px;margin:auto">
              <span style="text-align: left;">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{notice.content}}
                默认通知内容本课程将于9月1日开始，授课地点为综合楼109，请大家携带实验工具箱按时上课。同学们江姐为三人小组进行实验。第一次试验内容为晶体管的爆裂。
            </span></el-col>
            </el-row>

          <!-- <el-row style="margin: auto;display: flex;align-items: center;background-color:;margin-top:10px">
            <el-col :span="18"></el-col>
           <el-col :span="6"><el-button type="danger" plain size="mini" style="margin-bottom:0;justify-content:end" 
            @click="deletingFormVisible=true">删除</el-button></el-col>
          </el-row> -->
          
        </el-collapse-item>
        </el-collapse>
        </li>
      </ul>
      <!--  -->
      <p v-if="loading" style="margin-top: 10px" class="loading">
        <span></span>
      </p>
      <p v-if="noMore" style="margin-top: 10px; font-size: 13px; color: #ccc;margin-bottom: 2%;">
        没有更多了
      </p>
    </div>
  <!-- </el-main> -->
  </el-container>

</template>

<script>
  import axios from 'axios'

export default {
  inject:['reload'],
    data() {
        return {
            loading: false,
            count: 10,
            totalPages: 10,
            // notice: {
            //     noticeId: "3",
            //     title: "新通知默认标题",
            //     updateTime: "获取",
            //     content: "",
            //     courseId: "",
            // },
            notices: [{
                    noticeId: "1",
                    title: "第一条",
                    updateTime: "",
                    content: "",
                }],
            notice1: {
                noticeId: '',
                title: "不要看啦，没有更多了哦",
                updateTime: "",
                content: "这里是课程通知",
            },
        };
    },
    mounted() {
        
    },
    watch: {
        $route: {
            handler() {
              var courseId = parseInt(this.$route.params.id)
              this.$store.commit('GET_COURSEID', courseId)
              courseId = parseInt(this.$store.state.courseId)
              axios.get('http://localhost:14514/student/course/notice/list', {
                headers: {
                'token': this.$store.state.token
              },
                params: {
                'courseId': courseId,
                page: 1
              }
            }
        ).then(response => {
          this.notices = response.data.data.records
        })
      },
      deep: true,
      immediate: true
        },
    },
    methods: {
        infiniteLoad() {
            console.log("触底了");
            //滑到底部时进行加载
            this.loading = true;
            setTimeout(() => {
                let length = this.notices.push(this.notice1,this.notice1);
                this.count += 2;
                console.log("无限加载", length, "loading", this.loading);
                this.showLoading();
            }, 1500);
        },
        showLoading() {
            this.loading = false;
        },
    },
    computed: {
        noMore() {
            //当起始页数大于总页数时停止加载
            return this.notices.length >= this.totalPages - 1;
        },
        disabled() {
            return this.loading || this.noMore;
        },
    },
   
}
</script>

<style scoped>

.subTitle{
  margin-bottom: 5%;
  margin-top: 3%;
  font-size: 18px;
  cursor: pointer;
  color: #333;
  font-weight: bolder;}

  .noticeTitle{
    margin: auto;
    display: flex;
    justify-content: start;
    align-items:center ;
    font-size:larger; 
    font-family: 'Microsoft YaHei';
    font-weight: bolder;
  }

  .noticeTitle:hover{
    color:#ff4040;
    /* background-color: #409eff; */
  }
  .loading span {
  display: inline-block;
  width: 20px;
  height: 20px;
  border: 2px solid #409eff;
  border-left: transparent;
  animation: rotate 0.5s linear infinite;
  border-radius: 50%;
}
  .noticeDate{
    margin: auto;
    display: flex;
    justify-content: end;
    align-items:end;
    font-size: small; 
    font-family: 'Microsoft YaHei';
    color:#20A0FF
  }
  .title{
  font-weight: bold;
  letter-spacing: 0.2em;
  }
</style>

