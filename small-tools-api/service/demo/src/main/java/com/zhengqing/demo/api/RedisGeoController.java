package com.zhengqing.demo.api;

import com.zhengqing.common.api.BaseController;
import com.zhengqing.common.model.bo.RedisGeoPoint;
import com.zhengqing.common.util.RedisGeoUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p> 测试redis geo </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/29 17:46
 */
@Slf4j
@RestController
@RequestMapping("/web/api/demo/redis/geo")
@Api(tags = "test-redis-geo-api")
public class RedisGeoController extends BaseController {

    private final String GEO_KEY = "GEO_KEY";

    private String buildRedisKey(String key, String cityId) {
        return key + ":" + cityId;
    }

    /**
     * 使用redis+GEO，上报司机位置
     * 成都银泰城       http://localhost:20040/web/api/demo/redis/geo/addDriverPosition?cityId=666&driverId=001&lng=104.059477&lat=30.540611
     * 腾讯成都大夏B楼   http://localhost:20040/web/api/demo/redis/geo/addDriverPosition?cityId=666&driverId=002&lng=104.061967&lat=30.545361
     * 成都高新文化中心  http://localhost:20040/web/api/demo/redis/geo/addDriverPosition?cityId=666&driverId=003&lng=104.050658&lat=30.543457
     * 大源            http://localhost:20040/web/api/demo/redis/geo/addDriverPosition?cityId=666&driverId=004&lng=104.046002&lat=30.549537
     */
    @GetMapping("addDriverPosition")
    public Long addDriverPosition(String cityId, String driverId, Double lng, Double lat) {
        String redisKey = this.buildRedisKey(this.GEO_KEY, cityId);

        Long addNum = RedisGeoUtil.geoAdd(redisKey, new Point(lng, lat), driverId);

        List<Point> points = RedisGeoUtil.geoPos(redisKey, driverId);
        System.out.println("添加位置坐标点：" + points);

        return addNum;
    }

    /**
     * 使用redis+GEO，查询附近司机位置
     * OCG国际中心   http://localhost:20040/web/api/demo/redis/geo/getNearDrivers?cityId=666&lng=104.064391&lat=30.542866
     */
    @GetMapping("getNearDrivers")
    public List<RedisGeoPoint> getNearDrivers(String cityId, Double lng, Double lat) {
        String redisKey = this.buildRedisKey(this.GEO_KEY, cityId);
        // 1km内的数据
        return RedisGeoUtil.geoNear(redisKey, lng, lat, 1, RedisGeoCommands.DistanceUnit.KILOMETERS, 5);
    }

}

