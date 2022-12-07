<template>
  <div class="box">
    <div class="img">
      <img
        style="width: 880px; height: 580px"
        src="https://www.freemusic.ltd/markdown/backiee-213432-landscape.jpg"
        alt="图片"
      />
    </div>
    <div class="signUp">
      <div class="signUp-head">
        <span>用户注册</span>
      </div>
      <el-form
        :model="registerForm"
        :rules="rules"
        ref="registerForm"
        label-width="70px"
        class="demo-ruleForm"
      >
        <el-form-item prop="username" label="用户名">
          <el-input
            prefix-icon="el-icon-user-solid"
            v-model="registerForm.username"
            placeholder="用户名"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input
            prefix-icon="el-icon-lock"
            type="password"
            v-model="registerForm.password"
            placeholder="密码"
          ></el-input>
        </el-form-item>
        <el-form-item prop="avatar" label="头像">
          <el-upload
            class="avatar-uploader"
            action="/api/upload"
            name="pic"
            :multiple="true"
            :auto-upload="true"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img
              v-if="registerForm.avatar"
              :src="registerForm.avatar"
              class="avatar"
              alt="头像"
            />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div class="login-btn">
        <el-button @click="goBack(-1)" type="primary">取消</el-button>
        <el-button type="primary" @click="addSave">注册</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import LoginLogo from "../components/LoginLogo";
import { isEmail, isPhoneNum } from "@/plugins/validate";
import cities from "../assets/data/form";
import { addConsumer } from "@/api/Consumer";

let validateEmail = (rule, value, callback) => {
  if (!isEmail(value)) {
    callback(new Error("邮箱格式错误"));
  } else {
    callback();
  }
};

let validatePhoneNum = (rule, value, callback) => {
  if (!isPhoneNum(value)) {
    callback(new Error("手机号格式错误"));
  } else {
    callback();
  }
};

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Register",
  // eslint-disable-next-line vue/no-unused-components
  components: { LoginLogo },
  data() {
    return {
      registerForm: {
        username: "",
        password: "",
        sex: "",
        phoneNum: "",
        email: "",
        birth: "",
        introduction: "",
        location: "",
        avatar: "",
      },
      cities: [],
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        sex: [{ required: true, message: "请选择性别", trigger: "blur" }],
        phoneNum: [
          { required: true, message: "请输入电话号码", trigger: "blur" },
          { validator: validatePhoneNum, trigger: "blur" },
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { validator: validateEmail, trigger: "blur" },
        ],
        birth: [{ required: true, message: "请选择用户生日", trigger: "blur" }],
        introduction: [
          { required: true, message: "请输入用户签名", trigger: "blur" },
        ],
        location: [
          { required: true, message: "请输入用户所在地区", trigger: "blur" },
        ],
        avatar: [{ required: true, message: "请上传头像", trigger: "blur" }],
      },
    };
  },
  created() {
    this.cities = cities;
  },
  methods: {
    // 添加功能
    addSave() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          const Consumer = this.registerForm;
          addConsumer(Consumer).then((res) => {
            if (res.data.code === 200) {
              this.$message.success(res.data.msg);
              this.registerForm = [];
              setTimeout(() => {
                this.$router.push({ path: "/" });
              }, 2000);
            } else {
              this.$message.error(res.data.msg);
            }
          });
        }
      });
    },
    handleAvatarSuccess(res) {
      if (res.code === 20000) {
        this.registerForm.avatar = res.data;
        this.$message.success(res.message);
      } else {
        this.$message.error(res.message);
      }
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      return isJPG;
    },
    goBack(index) {
      this.$router.go(index);
    },
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/sign-up";
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  margin-bottom: 20px;
  width: 178px;
  height: 178px;
  display: block;
}
</style>
