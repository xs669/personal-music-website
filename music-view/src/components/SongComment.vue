<template>
  <div>
    <div class="comment">
      <h2>评论</h2>
      <div class="comment-msg">
        <div class="comment-img">
          <img :src="avatar" alt="用户头像" />
        </div>
        <el-input
          class="comment-input"
          type="textarea"
          :row="2"
          placeholder="请输入评论内容"
          v-model="textarea"
        ></el-input>
      </div>
      <el-button type="primary" class="sub-btn" @click="postComment"
        >评论</el-button
      >
    </div>
    <p style="color: #fff">精彩评论共：{{ commentList.length }}条评论</p>
    <hr />
    <ul class="popular" v-for="(item, index) in commentList" :key="index">
      <li>
        <div class="popular-img">
          <img :src="item.avatar" alt="用户头像" />
        </div>
        <div class="popular-msg">
          <ul>
            <li class="name">{{ item.username }}</li>
            <li class="time">{{ parseTime(item.createTime) }}</li>
            <li class="content">{{ item.content }}</li>
          </ul>
        </div>
        <div class="up" ref="up" @click="postUp(item.id, item.up, index)">
          <svg class="icon" style="cursor: pointer; color: #fff">
            <use xlink:href="#icon-zan"></use>
          </svg>
          {{ item.up }}
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import {
  addComment,
  cancelCommentUp,
  getAllCommentBySongId,
  getAllCommentBySongListId,
  updateCommentUp,
} from "@/api/Comment";

export default {
  name: "SongComment",
  props: ["playId", "type"],
  data() {
    return {
      textarea: "",
      like: false,
      commentList: [],
    };
  },
  computed: {
    ...mapGetters(["id", "avatar", "loginIn", "userId"]),
  },
  mounted() {
    if (!localStorage.getItem("consumerName")) {
      this.$store.commit(
        "setAvatar",
        "https://www.freemusic.ltd/avatar/1.jpeg"
      );
    }
  },
  created() {
    this.getComment();
  },
  methods: {
    postComment() {
      if (localStorage.getItem("consumerName")) {
        if (this.type === 1) {
          const Comment = {
            userId: this.userId,
            type: this.type,
            songId: "",
            songListId: this.playId,
            content: this.textarea,
            up: 0,
          };
          if (this.textarea === "" || this.textarea === null) {
            this.$message.warning("评论内容不能为空");
          } else {
            addComment(Comment).then((res) => {
              if (res.data.code === 200) {
                this.$message.success(res.data.msg);
                this.getComment();
                this.textarea = "";
              } else {
                this.$message.error(res.data.msg);
                this.textarea = "";
              }
            });
          }
        } else {
          const Comment = {
            userId: this.userId,
            type: this.type,
            songId: this.playId,
            songListId: "",
            content: this.textarea,
            up: 0,
          };
          if (this.textarea === "" || this.textarea === null) {
            this.$message.warning("评论内容不能为空");
          } else {
            addComment(Comment).then((res) => {
              if (res.data.code === 200) {
                this.$message.success(res.data.msg);
                this.getComment();
                this.textarea = "";
              } else {
                this.$message.error(res.data.msg);
                this.textarea = "";
              }
            });
          }
        }
      } else {
        this.$message.warning("未登录, 不能评论");
        this.textarea = "";
      }
    },
    getComment() {
      if (this.type === 1) {
        getAllCommentBySongListId(this.playId).then((res) => {
          if (res.data.code === 200) {
            this.commentList = res.data.data;
          }
        });
      } else {
        getAllCommentBySongId(this.playId).then((res) => {
          if (res.data.code === 200) {
            this.commentList = res.data.data;
          }
        });
      }
    },
    postUp(id, up, index) {
      const CommentDto = {
        id: id,
        up: up,
      };
      if (localStorage.getItem("consumerName")) {
        if (!this.like) {
          updateCommentUp(CommentDto).then((res) => {
            if (res.data.code === 200) {
              this.$message.success(res.data.msg);
              this.$refs.up[index].children[0].style.color = "#2aa3ef";
              this.like = true;
              this.getComment();
            } else {
              this.$message.error(res.data.msg);
            }
          });
        } else {
          cancelCommentUp(CommentDto).then((res) => {
            if (res.data.code === 200) {
              this.$message.success(res.data.msg);
              this.$refs.up[index].children[0].style.color = "#fff";
              this.like = false;
              this.getComment();
            } else {
              this.$message.error(res.data.msg);
            }
          });
        }
      } else {
        this.$message.warning("未登录, 不能点赞");
      }
    },
    parseTime(time) {
      let newTime = time.split("T");
      return newTime[0] + " " + newTime[1];
    },
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/song-comment";
</style>
