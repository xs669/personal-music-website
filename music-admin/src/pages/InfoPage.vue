<template>
  <div>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ consumerCount }}</div>
              <div>用户数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ songCount }}</div>
              <div>歌曲数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ singerCount }}</div>
              <div>歌手数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ songListCount }}</div>
              <div>歌单数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="12">
        <h3 class="mgb20">用户性别比例</h3>
        <div style="background-color: white">
          <ve-pie :data="consumerSex" :theme="options"></ve-pie>
        </div>
      </el-col>
      <el-col :span="12">
        <h3 class="mgb20">歌单类型分布</h3>
        <div style="background-color: white">
          <ve-histogram :data="SongListStyle" :theme="theme"></ve-histogram>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="12">
        <h3 class="mgb20">歌手性别比例</h3>
        <div style="background-color: white">
          <ve-ring :data="SingerSex"></ve-ring>
        </div>
      </el-col>
      <el-col :span="12">
        <h3 class="mgb20">歌手国籍分布</h3>
        <div style="background-color: white">
          <ve-histogram :data="SingerCountry" :theme="theme"></ve-histogram>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getConsumerCount, getConsumerCountBySex } from "@/api/Consumer";
import { getSongCount } from "@/api/Song";
import {
  getSingerCount,
  getSingerCountByLocation,
  getSingerCountBySex,
} from "@/api/Singer";
import { getAllStyle, getSongListCount } from "@/api/SongList";

export default {
  name: "InfoPage",
  data() {
    return {
      consumerCount: 0,
      songCount: 0,
      singerCount: 0,
      songListCount: 0,
      consumerSex: {
        columns: ["性别", "数量"],
        rows: [],
      },
      SingerSex: {
        columns: ["性别", "数量"],
        rows: [],
      },
      SongListStyle: {
        columns: ["风格", "数量"],
        rows: [],
      },
      SingerCountry: {
        columns: ["国籍", "数量"],
        rows: [],
      },
      options: {
        color: ["#ffc0cb", "#87cefa"],
      },
      theme: {
        color: ["#00BFFF"],
      },
    };
  },
  mounted() {
    this.getDataCount();
    this.CountConsumerBySex();
    this.CountSongListByStyle();
    this.CountSingerBySex();
    this.CountSingerByCountry();
  },
  methods: {
    CountSingerByCountry() {
      getSingerCountByLocation().then((res) => {
        if (res.code === 200) {
          let SingerCountryData = this.SingerCountry.rows;
          let Singer = res.data;
          for (let index in Singer) {
            if (index <= 6) {
              SingerCountryData.push({
                "国籍": Singer[index].location,
                "数量": Singer[index].number,
              });
            }
          }
        }
      });
    },
    CountSongListByStyle() {
      let Style = this.SongListStyle.rows;
      getAllStyle().then((res) => {
        if (res.code === 200) {
          let styleList = res.data;
          for (let index in styleList) {
            Style.push({
              "风格": styleList[index].style,
              "数量": styleList[index].number,
            });
          }
        }
      });
    },
    CountSingerBySex() {
      getSingerCountBySex().then((res) => {
        if (res.code === 200) {
          let SingerSexData = this.SingerSex.rows;
          let Singer = res.data;
          for (let index in Singer) {
            if (Singer[index].sex === 1) {
              SingerSexData.push({
                "性别": "男",
                "数量": Singer[index].number
              })
            } else if (Singer[index].sex === 0) {
              SingerSexData.push({
                "性别": "女",
                "数量": Singer[index].number
              })
            } else if (Singer[index].sex === 2) {
              SingerSexData.push({
                "性别": "组合",
                "数量": Singer[index].number
              })
            } else if (Singer[index].sex === 3) {
              SingerSexData.push({
                "性别": "不明",
                "数量": Singer[index].number,
              })
            }
          }
        }
      });
    },
    CountConsumerBySex() {
      getConsumerCountBySex().then((res) => {
        if (res.code === 200) {
          let Consumer = res.data;
          let ConsumerSexData = this.consumerSex.rows
          for (let index in Consumer) {
            if (Consumer[index].sex === 1) {
              ConsumerSexData.push({
                "性别": "男",
                "数量": Consumer[index].number
              })
            }
            else if(Consumer[index].sex === 0) {
              ConsumerSexData.push({
                "性别": "女",
                "数量": Consumer[index].number
              })
            }
            else {
              ConsumerSexData.push({
                "性别": "不明",
                "数量": Consumer[index].number
              })
            }
          }
        }
      });
    },
    getDataCount() {
      getConsumerCount().then((res) => {
        if (res.code === 200) {
          this.consumerCount = res.data;
        }
      });
      getSongCount().then((res) => {
        if (res.code === 200) {
          this.songCount = res.data;
        }
      });
      getSingerCount().then((res) => {
        if (res.code === 200) {
          this.singerCount = res.data;
        }
      });
      getSongListCount().then((res) => {
        if (res.code === 200) {
          this.songListCount = res.data;
        }
      });
    },
  },
};
</script>

<style scoped>
.grid-content {
  display: flex;
  align-items: center;
  height: 50px;
}

.grid-cont-center {
  flex: 1;
  font-size: 14px;
  color: darkgray;
  text-align: center;
}

.grid-num {
  font-size: 30px;
  font-weight: bold;
}
</style>
