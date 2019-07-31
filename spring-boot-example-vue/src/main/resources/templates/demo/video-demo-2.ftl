<!DOCTYPE html>
<html lang="en">
<head>
    <title>video-demo</title>
</head>

<body>
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.10/dist/vue.js"></script>
<script src="https://cdn.jsdelivr.net/npm/element-ui@2.11.1/lib/index.js"></script>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/element-ui@2.11.1/lib/theme-chalk/index.css">

<style type="text/css">
    .video-poster-div {
        position: relative;
        display: inline;
        left: 0;
        top: 0;
    }

    .video-poster {
        position: relative;
        left: 0;
        top: 0;
        width: 80px;
        padding: 5px 0px 5px 5px;
    }

    .video-poster:hover {
        cursor: pointer;
    }

    .play-btn {
        position: absolute;
        width: 40px;
        height: 40px;
        z-index: 99;
        left: 25px;
        top: -50px;
    }

    .play-btn:hover {
        cursor: pointer;
    }

    .img-thumb {
        display: inline-block;
        width: 80px;
        padding: 5px;
    }

    .img-thumb:hover {
        cursor: pointer;
    }
</style>

<div id="app">
    <el-table border :data="tableData" style="width: 100%">
        <el-table-column label="附件" width="310" height="200">
            <template slot-scope="scope">
                <div class="video-poster-div" @click="videoPlay(scope.row.videoUrl)">
                    <img :src="scope.row.poster" class="video-poster">
                    <img src="https://www.slatecube.com/images/play-btn.png" class="play-btn">
                </div>
                <div style="display: inline;">
                    <span v-for="(file,index) in scope.row.images">
                        <img class="img-thumb" :src="file.url" @click="showImages(index,scope.row.images)"/>
                    </span>
                </div>
            </template>
        </el-table-column>

        <el-table-column label="操作" width="180">
            <template slot-scope="scope">
                <el-button type="primary" @click="videoPlay(scope.row.videoUrl)" size="mini">播放视频</el-button>
            </template>
        </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible"
               top="15vh"
               @close="closeDialog">
        <video :ref="dialogVideo" :src="videoUrl" controls autoplay muted class="video" width="100%"></video>
    </el-dialog>

    <el-dialog title="" :visible.sync="filesView.show" @open="filesViewOpen" width="70%">
        <el-carousel ref="carouselView" v-bind:initial-index="filesView.index" v-bind:autoplay="false"
                     loop="false" indicator-position="outside" height="800px">
            <el-carousel-item v-for="file in filesView.files">
                <img class="img-original" :src="file.url"/>
            </el-carousel-item>
        </el-carousel>
    </el-dialog>
</div>

<script>
    new Vue({
        el: '#app',
        data: {
            dialogVisible: false,
            videoUrl: '',
            filesView: {'show': false, 'carouselReady': false},
            tableData: [{
                videoUrl: "https://product-online.cdn.bcebos.com/1548671344861263.mp4",
                poster: "https://product-online.cdn.bcebos.com/1559324768896248.png",
                images: [
                    {index: 1, url: "https://product-online.cdn.bcebos.com/1559324768804836.png"},
                    {index: 2, url: "https://product-online.cdn.bcebos.com/1559324768980860.png"}
                ]
            }, {
                videoUrl: "https://product-online.cdn.bcebos.com/1559705537074992.mp4",
                poster: "https://product-online.cdn.bcebos.com/1559324788514082.jpg",
                images: [
                    {index: 1, url: "https://product-online.cdn.bcebos.com/1559324788514082.jpg"},
                    {index: 2, url: "https://product-online.cdn.bcebos.com/1559324788514082.jpg"},
                    {index: 3, url: "https://product-online.cdn.bcebos.com/1559324788514082.jpg"},
                    {index: 4, url: "https://product-online.cdn.bcebos.com/1559324788514082.jpg"}
                ]
            }]
        },
        methods: {
            videoPlay: function (videoUrl) {
                console.log(videoUrl);
                this.dialogVisible = true;
                this.videoUrl = videoUrl;
            },
            closeDialog: function () {
                this.dialogVisible = false;
                this.videoUrl = '';
            },
            showImages: function (index, files) {
                this.filesView.show = true;
                this.filesView.files = files;
                this.filesView.index = index;
            },
            filesViewOpen: function () {
                if (this.filesView.carouselReady) {
                    this.$refs.carouselView.setActiveItem(this.filesView.index);
                }
                this.filesView.carouselReady = true;
            }
        }
    })
</script>
</body>
</html>
