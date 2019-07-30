<!DOCTYPE html>
<html lang="en">
<head>
    <title>video-player-demo</title>
</head>

<body>
<script src="https://cdn.jsdelivr.net/npm/video.js@6.13.0/dist/video.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.10/dist/vue.js"></script>
<script src="https://cdn.jsdelivr.net/npm/element-ui@2.11.1/lib/index.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue-video-player@5.0.2/dist/vue-video-player.js"></script>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/video.js@6.13.0/dist/video-js.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/element-ui@2.11.1/lib/theme-chalk/index.css">

<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">

<style type="text/css">
    /* Just a parent container for the videos */
    .videos {
        margin: 20px auto;
        max-width: 700px;
    }

    /* Individual video container */
    .video-wrap {
        position: relative;
        max-width: 700px;
        width: 100%;
        margin-bottom: 10px;
    }

    .video-wrap .placeholder {
        max-width: 700px;
        width: 100%;
    }

    .video-wrap .play-btn {
        position: absolute;
        max-width: 700px;
        width: 100px;
        height: 100px;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        line-height: 1; /* needed if using Bootstrap */
        text-align: center;
        color: #eaeaea;
        background-color: rgba(255, 255, 255, .4);
        border-radius: 50px;
        transition: all .2s ease;
    }

    .video-wrap .play-btn:hover,
    .video-wrap .play-btn:focus {
        color: #000;
        background-color: rgba(255, 255, 255, .8);
        cursor: pointer;
    }

    .play-btn::after {
        /*
        Font Awesome recommends these styles
        https://fontawesome.com/how-to-use/on-the-web/advanced/css-pseudo-elements
        */
        display: inline-block;
        font-style: normal;
        font-variant: normal;
        text-rendering: auto;
        -webkit-font-smoothing: antialiased;
        /*
        Define the font family, weight, and icon
        */
        font-family: "Font Awesome 5 Free";
        font-weight: 900;
        font-size: 60px;
        content: "\f04b";
        /* positioning tweaks */
        padding-top: 20px;
        padding-left: 10px;
    }
</style>

<script type="text/javascript">
    Vue.use(window.VueVideoPlayer)
</script>

<div id="app">
    <el-table border :data="tableData" style="width: 100%">
        <el-table-column label="视频" width="320" height="180">
            <template slot-scope="scope">
                <div class="videos">
                    <div class="video-wrap">
                        <div class="play-btn" @click="videoPlay(scope.row.videoUrl)"></div>
                        <img :src="scope.row.poster" width="320">
                    </div>
                </div>
            </template>
        </el-table-column>

        <el-table-column label="操作" width="180">
            <template slot-scope="scope">
                <el-button type="primary" @click="videoPlay(scope.row.videoUrl)" size="mini">播放</el-button>
            </template>
        </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible"
               top="15vh"
               destroy-on-close="true">
        <video-player class="vjs-default-skin vjs-big-play-centered"
                      ref="videoPlayer"
                      :options="playerOptions"
                      :playsinline="false"
                      @play="onPlayerPlay($event)"
                      @pause="onPlayerPause($event)">
        </video-player>
    </el-dialog>
</div>

<script>
    new Vue({
        el: '#app',
        data: {
            dialogVisible: false,
            playerOptions: {
                language: 'zh-CN',
                height: '360',
                sources: [{
                    type: "video/mp4",
                    src: ''
                }],
                techOrder: ['html5'],
                fluid: true,
                muted: true,
                autoplay: true,
                controls: true,
                playbackRates: [0.7, 1.0, 1.5, 2.0],
                notSupportedMessage: '此视频暂无法播放，请稍后再试'
            },
            tableData: [{
                videoUrl: "https://product-online.cdn.bcebos.com/1559705537074992.mp4",
                poster: "https://product-online.cdn.bcebos.com/1559324788514082.jpg"
            }, {
                videoUrl: "https://product-online.cdn.bcebos.com/1548671344861263.mp4",
                poster: "https://product-online.cdn.bcebos.com/1559324768804836.png"
            }]
        },
        methods: {
            videoPlay: function (videoUrl) {
                console.log(videoUrl);
                this.playerOptions.sources[0].src = videoUrl;
                this.dialogVisible = true;
            },
            onPlayerPlay: function () {
                console.log("onPlayerPlay");
            },
            onPlayerPause: function () {
                console.log("onPlayerPause");
            }
        }
    })
</script>
</body>
</html>
