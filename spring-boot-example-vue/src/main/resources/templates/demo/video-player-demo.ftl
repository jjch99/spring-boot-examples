<!DOCTYPE html>
<html lang="en">
<head>
    <title>demo</title>
</head>
<body>

<#--
 https://github.com/surmon-china/vue-video-player

 -->

<script src="https://cdn.jsdelivr.net/npm/video.js@6.13.0/dist/video.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.10/dist/vue.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/element-ui@2.11.1/lib/index.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue-video-player@5.0.2/dist/vue-video-player.min.js"></script>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/video.js@6.13.0/dist/video-js.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/element-ui@2.11.1/lib/theme-chalk/index.css">

<script type="text/javascript">
    Vue.use(window.VueVideoPlayer)
</script>

<div id="app">
    <el-button type="text" @click="dialogVisible=true">点击打开 Dialog</el-button>

    <el-dialog :visible.sync="dialogVisible"
               top="15vh">
        <video-player class="vjs-default-skin vjs-big-play-centered"
                      :options="playerOptions"
                      :playsinline="false"
                      @play="onPlayerPlay($event)"
                      @pause="onPlayerPause($event)">
        </video-player>
    </el-dialog>
</div>

<script type="text/javascript">
    new Vue({
        el: '#app',
        data: {
            dialogVisible: false,
            playerOptions: {
                language: 'zh-CN',
                height: '360',
                sources: [{
                    type: "video/mp4",
                    src: 'http://vjs.zencdn.net/v/oceans.mp4'
                }],
                techOrder: ['html5'],
                fluid: true,
                muted: true,
                autoplay: false,
                controls: true,
                playbackRates: [0.7, 1.0, 1.5, 2.0],
                notSupportedMessage: '此视频暂无法播放，请稍后再试'
            }
        },
        methods: {
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
