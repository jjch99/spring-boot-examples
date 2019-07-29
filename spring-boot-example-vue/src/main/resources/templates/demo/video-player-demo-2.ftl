<!DOCTYPE html>
<html lang="en">
<head>
    <title>demo</title>
</head>
<body>
<script src="https://unpkg.com/vue/dist/vue.js"></script>
<script src="https://unpkg.com/element-ui/lib/index.js"></script>

<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<link rel="stylesheet" href="https://unpkg.com/video.js/dist/video-js.css">

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

<div id="app">
    <el-table border :data="tableData" style="width: 100%">
        <el-table-column label="视频" width="320" height="180">
            <template scope="scope">
                <div class="videos">
                    <div class="video-wrap">
                        <div class="play-btn" @click="dialogVisible=true"></div>
                        <img src="https://product-online.cdn.bcebos.com/1563442402886808.png" width="320">
                    </div>
                </div>
            </template>
        </el-table-column>

        <el-table-column label="操作" width="180">
            <template scope="scope">
                <el-button type="primary" @click="dialogVisible=true" size="mini">播放</el-button>
            </template>
        </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" top="15vh">
        <span>
            <video src="https://product-online.cdn.bcebos.com/1563792469083752.mp4" controls autoplay muted
                   width="100%" height="auto">
            </video>
        </span>
    </el-dialog>
</div>

<script>
    new Vue({
        el: '#app',
        data: {
            dialogVisible: false
        },
        methods: {}
    })
</script>
</body>
</html>
