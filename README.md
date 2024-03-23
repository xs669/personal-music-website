# personal-music-website

## 项目介绍

本项目在[**B站SpringBoot + Vue音乐网站项目**](https://www.bilibili.com/video/BV1Ck4y127cg/?spm_id_from=333.337.search-card.all.click)的基础上做了以下优化：

- 音乐网站前台增加了排行榜的功能
- 音乐播放器在播放歌曲添加了单曲循环、随机播放、顺序播放的切换功能
- 在展示歌词界面增加了歌词滚动的功能
- 在我的音乐界面增加了查看用户历史播放音乐记录的功能。
- 在视频项目收藏功能的基础上新增了取消收藏的功能
- 在用户对歌曲评论页面新增了取消点赞的功能
- 在用户登录状态下可通过用户历史听歌记录和收藏歌曲和歌单记录为用户推荐类似的歌单和歌手
- 音乐网站后台界面增加了路由拦截功能，后台数据请求接口需要携带token才能访问，后台管理员登录状态用redis来控制，用于控制token的时效性。
- 项目中所有的静态资源文件均使用nginx静态服务器进行访问，这样静态文件可以与后台代码完全分离

## 项目运行流程

### 后台环境配置

#### 数据库配置

- 安装**MySQL8**数据库和可视化软件**Navicat**
- 数据库相关软件安装后完成在**Navicat**中导入**music-server/src/resources**目录下的**music.sql**文件

#### 开发环境配置

- **jdk18**
- **maven 3.8.6**
- **redis 5.0.14**
- **nginx 1.23.1**

#### nginx配置介绍

- 在**C**盘根目录创建**upload**文件夹，然后在**upload**文件夹里面分别创建**avatar**和**voice**文件夹
- **nginx**的安装目录需在**C**盘，安装完成并启动成功后在**nginx**安装目录下的**conf**文件夹里面修改**nginx.conf**配置文件，具体修改如下：

```xml
找到listen 80,然后在它下面添加或替换如下配置

        listen       80;

        server_name  localhost;

        sendfile        on;

        keepalive_timeout  65;

        charset utf-8;

        #access_log  logs/host.access.log  main;

        location / {

              add_header 'Access-Control-Allow-Origin' $http_origin;
              add_header 'Access-Control-Allow-Credentials' 'true';
              add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS';
              add_header 'Access-Control-Allow-Headers' 'DNT,web-token,app-token,Authorization,Accept,Origin,Keep-Alive,User-Agent,X-Mx-ReqToken,X-Data-Type,X-Auth-Token,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range';
              add_header 'Access-Control-Expose-Headers' 'Content-Length,Content-Range';
              if ($request_method = 'OPTIONS') {
                  add_header 'Access-Control-Max-Age' 1728000;
                  add_header 'Content-Type' 'text/plain; charset=utf-8';
                  add_header 'Content-Length' 0;
                  return 204;
              }

	      root /upload/;
              index  index.html index.htm;	# 需要转发的url地址
        }

        location ^~/api/ {
                proxy_pass http://localhost:8889/;
       }

        location ^~/apk/ {
                proxy_pass http://localhost:8889/admin/;
       }
```

- 配置修改完成重启一下nginx，若没有出现错误则nginx配置完成。

### 前端环境配置

- **nodejs 16.16.0**
- **nodejs**安装完成后配置一下**npm**的镜像源，然后用**npm**命令全局安装**yarn**软件包管理器

### 项目运行

#### 后台运行

- 用**IDEA2022**版本打开**music-server**目录下的所有文件，然后等待项目下载**maven**依赖
- 依赖下载完成后修改一下**resource**里面的配置文件，按照自己的配置的环境进行修改
- 配置文件的内容修改完成后，需要在**music**数据库里面的**admin**表中添加后台管理员的用户名、密码和角色，密码是通过springsecurity加密生成后存储到数据库，角色的话将其设为**admin**

#### 前台运行

- 使用**yarn**或**npm**命令安装所需模块
- 模块安装完成可直接打包
- 打包完成后将打包文件放入到上面创建好**upload**文件夹中，前台的文件夹命名为**view**，后台命名为**admin**
- 上一步完成后前台访问地址为：http://localhost/view
- 后台访问地址为：http://localhost/admin
- 初次访问由于数据库中没有那些歌曲或歌单的资源文件，前台页面展示出来全是空白的，可通过登录后台添加歌手、歌曲、歌单等信息以供前台显示。

### 运行截图
![image](https://github.com/xs669/personal-music-website/assets/76900699/81527508-778f-4cf7-842f-49c9de87952a)
![image](https://github.com/xs669/personal-music-website/assets/76900699/12677a92-a5cb-430e-a1d7-ad174a4e1525)
![image](https://github.com/xs669/personal-music-website/assets/76900699/e4794b35-075b-4733-a3b8-7eef77c87ed9)
![image](https://github.com/xs669/personal-music-website/assets/76900699/25101001-aafd-41ad-84ec-05ac9ef7ed6c)
![image](https://github.com/xs669/personal-music-website/assets/76900699/0673a6e4-ccfe-463e-a683-1e2ab035c18c)
![image](https://github.com/xs669/personal-music-website/assets/76900699/01854de1-bc08-40bf-ac51-4bdd8b459e51)
![image](https://github.com/xs669/personal-music-website/assets/76900699/911eab8f-7a98-4d36-bd52-48b577d6ee21)
![image](https://github.com/xs669/personal-music-website/assets/76900699/0a9c7c12-68dc-4cd6-a30c-07836c6fc693)
![image](https://github.com/xs669/personal-music-website/assets/76900699/bae0e97b-268d-4be1-9542-c2da8a74998f)
![image](https://github.com/xs669/personal-music-website/assets/76900699/0f824dd3-2145-4b5e-a072-3a7048107060)
![image](https://github.com/xs669/personal-music-website/assets/76900699/626f7b77-1285-48c2-8701-9ae3364b3f75)
![image](https://github.com/xs669/personal-music-website/assets/76900699/6055cfb6-6831-418a-9f1d-cbd075e15893)
![image](https://github.com/xs669/personal-music-website/assets/76900699/2853025c-eb19-4f3c-bfd5-4d34bef988f9)
![image](https://github.com/xs669/personal-music-website/assets/76900699/43de7650-7418-44bd-a92f-85578ef2faf3)
![image](https://github.com/xs669/personal-music-website/assets/76900699/20360eaa-3eb6-4bc1-829d-1e0e5ad11f8d)
![image](https://github.com/xs669/personal-music-website/assets/76900699/f8666597-9431-493b-8141-5e4dec564530)
![image](https://github.com/xs669/personal-music-website/assets/76900699/b8871930-684a-4551-87af-b1724edbaa83)
![image](https://github.com/xs669/personal-music-website/assets/76900699/db41de6f-b3b4-49ac-812e-c0d78ca59a9e)
