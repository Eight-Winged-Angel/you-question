<template>
  <el-container style="display: flex; align-items: center;justify-items: center ;">
    <el-main style="display: flex; align-items: center;justify-items: center ;">
      <el-tabs style="width: 330px">
        <el-tab-pane label="账号密码登录">
          <el-form
            ref="passwordLoginFormRef"
            :model="loginForm"
            :rules="passwordLoginRules"
            label-width="0px"
            class="LoginForm"
            @blur="clearPasswordForm"
          >
            <el-form-item prop="phone">
              <el-input
                placeholder="请输入手机号"
                v-model="loginForm.phone"
                prefix-icon="el-icon-phone"
              ></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                placeholder="请输入密码"
                type="password"
                v-model="loginForm.password"
                prefix-icon="el-icon-lock"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" class="btn1" @click="passwordLogin"
                >登录</el-button
              >
              <el-button class="btn2" @click="toRegister">注册</el-button>
            </el-form-item>
            <el-form-item prop="agree1">
              <el-checkbox-group v-model="loginForm.agree1">
                <el-checkbox  name="agree1">我已阅读并同意<a>《服务协议》</a>、<a>《隐私权政策》</a></el-checkbox>
              </el-checkbox-group>
               
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="短信验证码登录">
          <el-form
            ref="codeLoginFormRef"
            :model="verifyForm"
            :rules="codeLoginRules"
            label-width="0px"
            class="LoginForm"
            @blur="clearCodeForm"
          >
            <el-form-item prop="phone">
              <el-input
                placeholder="请输入手机号"
                v-model="verifyForm.phone"
                prefix-icon="el-icon-phone"
              ></el-input>
            </el-form-item>
            <el-form-item prop="code">
              <el-row :gutter="15">
                <el-col :span="15">
                  <el-input
                    id="codeInput"
                    placeholder="请输入验证码"
                    v-model="verifyForm.code"
                    prefix-icon="el-icon-setting"
                  ></el-input>
                </el-col>
                <el-col :span="6">
                  <el-button @click="getCode">获取验证码</el-button>
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" class="btn1" @click="codeLogin"
                >登录</el-button
              >
              <el-button class="btn2" @click="toRegister">注册</el-button>
            </el-form-item>
            <el-form-item>
            </el-form-item>
            <el-form-item prop="agree2">
              <el-checkbox-group v-model="verifyForm.agree2">
                <el-checkbox  name="agree2">我已阅读并同意<a>《服务协议》</a>、<a>《隐私权政策》</a></el-checkbox>
              </el-checkbox-group>
               
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>

    </el-main>
    <el-footer>
      
    </el-footer>
  </el-container>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      activeName: "first",
      isActive: true, // 用于实现切换登录，作为判断
      isActiveIndex: 0,
      loginForm: {
        phone: "12312312312",
        password: "114514",
        token:"",
        agree1:[],
      },
      // 12312312312
      // 18955690067
      verifyForm: {
        phone: "18513244990",
        code: "",
        token:"",
        agree2:[],
      },
      agree1:[],
      agree2:[],

      //表单的数据验证规则：必须满足某些条件才是合法输入数据
      passwordLoginRules: {
        phone: [
          { required: true, message: "请输入正确格式的手机号", trigger: "blur" },
          { min: 11, max: 11, message: "11位数字", trigger: "blur" },
        ],

        password: [
          { required: true, message: "请输入正确格式的密码", trigger: "blur" },
          { min: 6, max: 16, message: "长度在6位-16位之间", trigger: "blur" },
        ],

        agree1:[
        { required: true, message: "请阅读并同意", trigger: "change" },
        ]
      },

      codeLoginRules: {
        phone: [
          { required: true, message: "请输入正确格式的手机号", trigger: "blur" },
          { min: 11, max: 11, message: "11位数字", trigger: "blur" },
        ],
        code: [
          { required: true, message: "请输入正确格式的验证码", trigger: "blur" },
          // { min: 8, max:15, message: '长度在8位-16位之间', trigger: 'blur' },
        ],
        agree2:[
        { required: true, message: "请阅读并同意", trigger: "change" },
        ]
      },
    };
  },
  methods: {
    //登录预验证
    passwordLogin() {
      this.$refs.passwordLoginFormRef.validate(async (valid) => {
        console.log('valid');
        //黑马程序员课程第22p，启动MySQL数据库和API接口的app.js
          if (!valid) return;


          axios({
          method:'POST',
          url:'http://localhost:14514/auth/login/password',
          data:this.loginForm
        }).then(response=>{
          console.log(response.data)
          if (response.status !== 200)
          return this.$message.error("登录失败！");
          this.$message.success("登录成功！");

          //课程第24p
          //保存登录成功的token到sessionStorage
          this.$store.commit('GET_TOKEN',  response.data.data.token)
          // var role = response.data.data.role[0].substring(5)
          console.log("axcscxcs",this.$store.state.token)

          window.sessionStorage.setItem('token', response.data.data.token);
          //通过编程式导航跳转到后台主页，路由地址为/home

          // 根据是教师还是学生，分别去教师、学生的主页
          // this.$router.push("/teacher");

          if(response.data.data.role.length>1)
          {this.$router.push("/teacher");}
          else if (response.data.data.role[0].substring(5) == 'teacher')
          {this.$router.push("/teacher");}
          else 
          {this.$router.push("/student");}
          console.log("bbbbbbbbbbbbbbbbb")


          //尚未添加home主页，在课程p24后段
        })

        
        
      });
    },
    getCode() {
      axios.get('http://localhost:14514/auth/code/send',{
          // headers:{
          //   'token':window.sessionStorage.getItem('token')
          // },
          params: {
            'phone':this.verifyForm.phone,
            
          }
        }).then(response=>{
          console.log("获得验证码",response)
          
          // console.log(this.courseInfo)
          // window.sessionStorage.setItem("courseId", response.data.data);

  })
        this.dismissingFormVisible=false;

        this.$message({
          message: '获取验证码成功！',
          type: 'success',
          center: true
        });
    },

    codeLogin() {
      console.log('valid',this.verifyForm.phone,this.verifyForm.code);
      this.$refs.codeLoginFormRef.validate(async (valid) => {
        console.log('valid',this.verifyForm.phone,this.verifyForm.code);
        //黑马程序员课程第22p，启动MySQL数据库和API接口的app.js
          if (!valid) return;


          axios({
          method:'POST',
          url:'http://localhost:14514/auth/login/verifyCode',
          data:{
            'phone':this.verifyForm.phone,
            'code':this.verifyForm.code,
          }
        }).then(response=>{
          console.log(response.data)
          if (response.status !== 200)
          return this.$message.error("登录失败！");
          this.$message.success("登录成功！");

          //课程第24p
          //保存登录成功的token到sessionStorage
          this.$store.commit('GET_TOKEN',  response.data.data.token)
          // var role = response.data.data.role[0].substring(5)
          // console.log("axcscxcs",this.$store.state.token,response.data.data.role[0].substring(5),role)

          // window.sessionStorage.setItem('token', response.data.data);
          //通过编程式导航跳转到后台主页，路由地址为/home

          // 根据是教师还是学生，分别去教师、学生的主页
          if(response.data.data.role.length>1)
          {this.$router.push("/teacher");}
          else if (response.data.data.role[0].substring(5) == 'teacher')
          {this.$router.push("/teacher");}
          else 
          {this.$router.push("/student");}
          console.log("bbbbbbbbbbbbbbbbb")
          //尚未添加home主页，在课程p24后段
        })
        // axios.post("https://cupk.club/api/auth/login/password")
        //   .then(res=>{
        //      successResponse => {
        //       if (successResponse.data.code === 200) {
        //           this.$router.replace({path: ''})
        //       }else{
        //           console.log("账号或密码错误");
        //           this.loginForm.message="账号或密码错误";
        //       }
        //   }
        // })
        //   .catch(error=>{});
        // const {   res } = await this.$http.post("auth/login/password", this.loginForm);

        // console.log(res);
        //需要运行MySQL数据库和api接口！！

        //课程第23p
        //如果状态码为200，则登陆成功
        
        
      });
      
    },

    clearPasswordForm() {
      this.$refs.passwordLoginFormRef.clearValidate();
    },
    clearCodeForm() {
      this.$refs.codeLoginFormRef.clearValidate();
    },

    toRegister() {
      this.$bus.$emit("showRegister");
    },
  },
};
</script>

<style scoped>
</style>