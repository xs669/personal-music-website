<template>
  <div class="box">
    <div class="img">
      <img
        style="width: 880px; height: 580px"
        src="https://www.freemusic.ltd/photos/backiee-102533-landscape.jpg"
        alt="图片"
      />
    </div>
    <div class="login">
      <div class="login-head">
        <span>用户登录</span>
      </div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
        <el-form-item prop="username">
          <el-input
            prefix-icon="el-icon-user-solid"
            v-model="ruleForm.username"
            placeholder="用户名"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            prefix-icon="el-icon-lock"
            type="password"
            v-model="ruleForm.password"
            placeholder="密码"
          ></el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="goBack(-1)">取消</el-button>
          <el-button type="primary" @click="submitForm">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Logo from "../components/Logo";
import { login } from "@/api/Consumer";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  // eslint-disable-next-line vue/no-unused-components
  components: { Logo },
  data() {
    return {
      ruleForm: {
        username: "",
        password: "",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
    };
  },
  computed: {
    ...mapGetters(["loginIn"]),
  },
  methods: {
    submitForm() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          const LoginVo = this.ruleForm;
          login(LoginVo).then((res) => {
            if (res.data.code === 200) {
              const consumer = res.data.data;
              localStorage.setItem("consumerName", consumer.username);
              this.$store.commit("setLoginIn", !this.loginIn);
              this.$store.commit("setUserId", consumer.id);
              this.$store.commit("setUserName", consumer.username);
              this.$store.commit("setAvatar", consumer.avatar);
              this.$router.push({ path: "/" });
            } else {
              this.$message.error(res.data.msg);
            }
          });
        }
      });
    },
    goBack(index) {
      this.$router.go(index);
    },
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/login-in";
</style>
