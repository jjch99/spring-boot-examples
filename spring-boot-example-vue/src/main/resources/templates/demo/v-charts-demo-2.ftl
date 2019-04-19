<@page title="v-charts-demo">

<script src="https://unpkg.com/vue/dist/vue.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<script src="https://unpkg.com/echarts/dist/echarts.min.js"></script>
<script src="https://unpkg.com/v-charts/lib/index.min.js"></script>

<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<link rel="stylesheet" href="https://unpkg.com/v-charts/lib/style.min.css">

<div id="app">
    <el-button @click="loadChartData()">点击刷新</el-button>
    <ve-line :data="chartData"></ve-line>
</div>

<script>
    axios.defaults.baseURL = '${contextPath!}';

    new Vue({
        el: '#app',
        data: {
            chartData: {}
        },
        methods: {
            loadChartData: function () {
                let ref = this;
                axios.get('/api/demo/v-charts-demo')
                        .then(function (response) {
                            ref.chartData = response.data.data;
                        })
                        .catch(function (error) {
                            console.log(error)
                        });
            }
        },
        mounted: function () {
            let ref = this;
            ref.loadChartData();
        }
    })
</script>
</@page>
