import { mapGetters } from "vuex";
import {
  addPlayCount,
  gerAllSongAndSingerNameBySingerId,
  getAllSongBySongListId,
  getSongByPlayCount,
} from "@/api/Song";
import { getAllCollectByConsumerId } from "@/api/Collect";
import { addRecentSong } from "@/api/RecentSong";

export const mixins = {
  computed: {
    ...mapGetters(["userId", "isPlay", "control", "recentSongList"]),
  },
  methods: {
    toPlay: function (
      id,
      url,
      pic,
      index,
      name,
      singerName,
      lyric,
      introduction
    ) {
      this.$store.commit("setId", id);
      this.$store.commit("setUrl", url);
      this.$store.commit("setPicUrl", pic);
      this.$store.commit("setListIndex", index);
      this.$store.commit("setTitle", name);
      this.$store.commit("setArtist", singerName);
      this.$store.commit("setLyric", this.parseLyric(lyric));
      this.$store.commit("setIntroduction", introduction);
      this.$store.commit("setIsPlay", true);
      this.addPlayCount(id);
      let RecentSong = {
        userId: this.userId,
        songId: id,
        count: 1,
      };
      if (localStorage.getItem("consumerName")) {
        this.addRecentSong(RecentSong);
      }
    },
    addRecentSong(RecentSong) {
      addRecentSong(RecentSong).then((res) => {
        if (res.data.code === 200) {
          console.log(res.data.msg);
        }
      });
    },
    addPlayCount(id) {
      let SongVo = {
        id: id,
      };
      addPlayCount(SongVo).then((res) => {
        if (res.data.code === 200) {
          console.log(res.data.msg);
        }
      });
    },
    getSongByPlayCount() {
      getSongByPlayCount().then((res) => {
        if (res.data.code === 200) {
          this.$store.commit("setListOfSongs", res.data.data);
        }
      });
    },
    getSongOfSingerId() {
      gerAllSongAndSingerNameBySingerId(this.$route.query.id).then((res) => {
        if (res.data.code === 200) {
          this.$store.commit("setListOfSongs", res.data.data);
        }
      });
    },
    getUserCollectData() {
      getAllCollectByConsumerId(this.userId).then((res) => {
        if (res.data.code === 200) {
          this.$store.commit("setCollectList", res.data.data);
        } else {
          this.$store.commit("setCollectList", []);
        }
      });
    },
    getSongListDetailById() {
      getAllSongBySongListId(this.$route.query.id).then((res) => {
        if (res.data.code === 200) {
          this.$store.commit("setListOfSongs", res.data.data);
        } else {
          this.$store.commit("setListOfSongs", []);
        }
      });
    },
    parseLyric(text) {
      let result = [];
      let lines = text.split("\n");
      for (let line of lines) {
        let value = line.split("]")[1];
        let times = line.split("]")[0] + "]";
        let time = times.slice(1, -1).split(":");
        if (value !== "" && time !== "") {
          result.push([
            parseInt(time[0], 10) * 60 + parseFloat(time[1]),
            value,
          ]);
        }
      }
      result.sort(function (a, b) {
        return a[0] - b[0];
      });
      return result;
    },
  },
};
