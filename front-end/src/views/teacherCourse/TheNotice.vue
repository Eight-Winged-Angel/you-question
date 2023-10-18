<template>
  <el-container
      style="margin: auto;
          display: flex;
          justify-content: center;
          align-items:center;
          background-color: #eaeaea;
          ">

    <el-header style="margin-top:1.3%">
      <!-- <div slot="header" > -->
      <el-row style="width:100%;margin: auto;
          display: flex;align-items: center;justify-items:center;margin-bottom:10px;margin-top: 20px;">
        <el-col :span="18"
                class="title" style=" margin: auto;
          display: flex;
          justify-content: start;
          align-items:center ;
          font-size:larger; 
          font-family: 'Microsoft YaHei';">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;课程通知
        </el-col>
        <el-col :span="5"></el-col>
        <el-col :span="1" style="">
          <el-button style="margin-left:30px ;margin-right:10px ;justify-content: end" type="primary"
                     @click="dialogVisible=true">
            发布新通知
          </el-button>


        </el-col>
      </el-row>
      <!-- 以下和弹窗有关 -->
      <el-dialog
          title="请输入通知内容"
          :visible.sync="dialogVisible"
          width="30%"
          :before-close="handleClose"
      >

        <el-form ref="formRef" :label-position="labelPosition" label-width="80px" :model="notice">
          <el-form-item label="通知标题" prop="title" :rules="[
                  { required: true, message: '通知标题不能为空', trigger: 'blur'}
                ]">
            <el-input v-model="notice.title"></el-input>
          </el-form-item>

          <el-form-item label="通知内容" prop="content" :rules="[
                  { required: true, message: '通知内容不能为空', trigger: 'blur'}
                ]">
            <el-input
                type="textarea"
                :autosize="{ minRows: 3, maxRows: 7}"
                show-word-limit
                maxlength="100"

                placeholder="请输入通知内容"
                v-model="notice.content">
            </el-input>
          </el-form-item>
          <el-form-item label="通知配图" prop="picture">
            <el-upload
                class="upload"
                action="https://jsonplaceholder.typicode.com/posts/"

                :file-list="fileList"
                list-type="picture"
                :limit="1"
                :multiple="false"
                style="margin-left: 0%">
              <el-button size="small" type="primary" size:larger>点击上传</el-button>
              <!-- <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button> -->
              <div slot="tip" class="el-upload__tip">只接受jpg/png文件，且不超过500kb</div>
              <!-- :on-preview="handlePreview"
                                :on-remove="handleRemove"
                                :on-change="handleChange" -->
            </el-upload>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer" style="margin-top:0">
              <el-button type="primary" @click="confirmNew">确 定</el-button>
            </span>
      </el-dialog>
    </el-header>


    <el-main>
      <div class="box" style="width:600px;margin-top:5.3%;background-color: ;">
        <ul
            class="list"
            v-infinite-scroll="infiniteLoad"
            infinite-scroll-disabled="disabled"

        >
          <li v-for="notice in notices" :key="notice.noticeId" :notice="notice"
              class="list-item" style="margin-top:1.5%;background-color:;border-radius:4px">
            <!-- style=";background-color:#f6dcd7" -->
            <el-collapse style=";background-color:;border-radius:4px" @change="clickNotice(notice.noticeId)">
              <el-collapse-item name="2" style="font-weight:normal;;">
                <template slot="title">
                  <el-row style="width:100%;margin: auto;
              display: flex;align-items: center;">
                    <el-col :span="16"
                            class="noticeTitle title"
                            style="background-color:">
                      &nbsp;&nbsp;
                      {{ notice.title }}
                    </el-col>
                    <el-col :span="8"
                            class="noticeDate"
                            style="background-color:;">
                      {{ notice.updateTime.substring(0, 10) }}
                      &nbsp;&nbsp;
                    </el-col>
                  </el-row>
                </template>
                <el-row style="margin: auto;display: flex;align-items: center;justify-content: start;">
                  <el-col :span="24"
                          style="margin-top:4%;text-align: left;letter-spacing:normal; padding-left: 20px;padding-right: 20px;margin:auto">
              <span style="text-align: left;">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ notice.content }}
                默认通知内容本课程将于9月1日开始，授课地点为综合楼109，请大家携带实验工具箱按时上课。同学们江姐为三人小组进行实验。第一次试验内容为晶体管的爆裂。
            </span></el-col>
                </el-row>

                <el-row style="margin: auto;display: flex;align-items: center;background-color:;margin-top:10px">
                  <el-col :span="18"></el-col>
                  <el-col :span="6">
                    <el-button type="danger" plain size="mini" style="margin-bottom:0;justify-content:end"
                               @click="deletingFormVisible=true">删除
                    </el-button>
                  </el-col>
                </el-row>

              </el-collapse-item>
            </el-collapse>

            <el-dialog
                title="真的要删除通知吗？"
                :visible.sync="deletingFormVisible"
                width="30%"
            >
              <el-button type="danger" @click="confirmDelete()">确 定</el-button>
              <!-- </span> -->
            </el-dialog>

          </li>
        </ul>

        <!--  -->
        <p v-if="loading" style="margin-top: 10px" class="loading">
          <span></span>
        </p>
        <p v-if="noMore" style="margin-top: 10px; font-size: 13px; color: #ccc">
          没有更多了
        </p>
      </div>
    </el-main>

  </el-container>

</template>

<script>
// import {nanoid} from 'nanoid'
import axios from 'axios'

export default {
  inject: ['reload'],
  data() {

    return {

      fileList: [{
        name: 'noticePic.jpeg',
        url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
      }],
      labelPosition: 'right',

      dialogVisible: false,
      deletingFormVisible: false,

      loading: false,
      totalPages: 100,

      notices: [{
        noticeId: 1,
        title: '第一条',
        updateTime: '',
        content: 'bbbbbb'
      }],

      notice1: {  //试用通知，填充下方用
        noticeId: 2,
        title: '快去添加通知吧',
        updateTime: '',
        content: '快去添加通知吧'
      },

      notice: {   //新通知
        // brief:{
        title: '新通知默认标题',
        content: ''

        // },
        // detail:{
        //   updateTime:'',//create time
        //   noticeId:2,
        // }
      }
    }
  },

  watch: {
    $route: {
      handler() {
        var courseId = parseInt(this.$route.params.id)
        this.$store.commit('GET_COURSEID', courseId)
        courseId = parseInt(this.$store.state.courseId)
        axios.get('http://localhost:14514/teacher/course/notice/list', {
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
    }
  },
  methods: {
    clickNotice(noticeId) {
      this.$store.commit('GET_NOTICEID', noticeId)
    },
    confirmDelete() {
      this.deletingFormVisible = false;
      // api  删除通知


      let courseId = parseInt(this.$store.state.courseId)
      let noticeId = parseInt(this.$store.state.noticeId)

      axios.get('http://localhost:14514/teacher/course/notice/delete', {
        headers: {
          'token': this.$store.state.token
        },
        params: {
          'courseId': courseId,
          'noticeId': noticeId
        }
      }).then(response => {
        this.$message({
          message: '删除通知成功！',
          type: 'success',
          center: true
        });
        // this.reload();
        location.reload();
      })
    },
    handleClose(done) {
      this.$confirm('确认取消？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    },

    confirmNew() {
      this.dialogVisible=false
      this.$refs.formRef.validate(valid => {
        if (!valid) return;
        this.dialogVisible = false;
        let courseId = parseInt(this.$store.state.courseId)
        axios.post('http://localhost:14514/teacher/course/notice/add', {
          'title': this.notice.title,
          'content': this.notice.content,
          'courseId': courseId
        },{
        headers: {
          'token': this.$store.state.token
        } }).then(res => {

          if (res.data.code === '00000') {
            window.setTimeout(function () {
             window.location.reload();
      },1000)
            this.$notify.success({
              title: '发布成功',
              content: '快去通知公告看看吧'

            })
            return
          }

          this.$notify.error({
            title: '发布失败',
            content: '请稍候重试'
          })
        });
      })
    },

    infiniteLoad() {
      //滑到底部时进行加载
      this.loading = true;
      setTimeout(() => {
        let length = this.notices.push(this.notice1, this.notice1);
        this.count += 2;
        this.showLoading();
      }, 1500);
    },
    showLoading() {
      this.loading = false;
    }
  },
  computed: {
    noMore() {
      //当起始页数大于总页数时停止加载
      return this.notices.length >= this.totalPages - 1;
    },
    disabled() {
      return this.loading || this.noMore;
    }
  }
}
</script>

<style scoped>

.subTitle {
  margin-bottom: 5%;
  margin-top: 3%;
  font-size: 18px;
  cursor: pointer;
  color: #333;
  font-weight: bolder;
}

.noticeTitle {
  margin: auto;
  display: flex;
  justify-content: start;
  align-items: center;
  font-size: larger;
  font-family: 'Microsoft YaHei';
  font-weight: bolder;
}

.noticeTitle:hover {
  color: #ff4040;
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

.noticeDate {
  margin: auto;
  display: flex;
  justify-content: end;
  align-items: end;
  font-size: small;
  font-family: 'Microsoft YaHei';
  color: #20A0FF
}

.title {
  font-weight: bold;
  letter-spacing: 0.2em;
}
</style>

