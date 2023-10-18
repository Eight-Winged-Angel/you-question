<template>
  <el-container class="vh100">
    <el-dialog append-to-body :visible.sync="dialogVisible" width="50%">
      <div>
        <Register :newResgister="newResgister" />
      </div>
    </el-dialog>

    <el-header class="display-flex" style="background-color: #cee1fd" height="30vh">
      <the-header class="display-flex"></the-header>
    </el-header>
    <el-main>
      <custom-login></custom-login>
    </el-main>
    <el-footer class="display-flex text-info" height="15vh">
      <the-foot class="display-flex"></the-foot>
    </el-footer>
  </el-container>
</template>

<script>
import TheFoot from "@/components/home/TheFooter";
import TheHeader from "@/components/home/TheHeader";
import TheNav from "@/components/home/TheNav";
import CustomLogin from "@/components/CustomLogin";
import Register from "@/components/Register";

export default {
  components: {
    CustomLogin,
    TheHeader,
    TheFoot,
    TheNav,
    Register,
  },
  data() {
    return {
      dialogVisible: false,
      newResgister: false,
    };
  },
  mounted() {
    let that = this;
    this.$bus.$on("showRegister", () => {
      that.dialogVisible = true;
      that.newResgister = true;
      console.log("打开dialog", this.newResgister);
    });
    this.$bus.$on("closeRegister", () => {
      that.dialogVisible = false;
      that.newResgister = false;
    });
  },
  watch: {
    dialogVisible(newName, oldName) {
      if (oldName === true && newName === false) {
        this.newResgister = false;
        this.$router.push({ path: "/" });
        console.log("关闭dialog", this.newResgister);
      }
    },
  },
};
</script>

<style scoped>
.vh100 {
  height: 100vh;
}

.display-flex {
  margin: auto;
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
}

.text-info {
  color: #909399;
}
</style>
