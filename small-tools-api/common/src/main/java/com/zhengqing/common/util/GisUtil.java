package com.zhengqing.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 位置范围-工具类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/11/27 14:38
 */
public class GisUtil {

    public static void main(String[] args) {
        String points =
                "[[104.055529,30.537894],[104.064413,30.542884],[104.056044,30.542625]]";
        List<List> list = JSONObject.parseArray(points, List.class);

        List<PointBO> pointList = list.stream().map(i -> {
            PointBO point = new PointBO();
            point.setLon(Double.parseDouble(i.get(0).toString()));
            point.setLat(Double.parseDouble(i.get(1).toString()));
            return point;
        }).collect(Collectors.toList());

        PointBO p1 = new PointBO(104.057546, 30.541517);
        PointBO p2 = new PointBO(104.066902, 30.538486);
        System.out.println("p1:" + isPtInPoly(p1.getLon(), p1.getLat(), pointList));
        System.out.println("p2:" + isPtInPoly(p2.getLon(), p2.getLat(), pointList));
    }

    /**
     * 判断经纬度是否在多边形点范围内
     *
     * @param lon       经度
     * @param lat       纬度
     * @param pointList 范围
     * @return true->是 false->否
     */
    public static boolean isPtInPoly(double lon, double lat, List<PointBO> pointList) {
        int iSum, iCount, iIndex;
        double dLon1, dLon2, dLat1, dLat2, dLon;
        if (pointList.size() < 3) {
            return false;
        }
        iSum = 0;
        iCount = pointList.size();
        for (iIndex = 0; iIndex < iCount; iIndex++) {
            if (iIndex == iCount - 1) {
                dLon1 = pointList.get(iIndex).getLon();
                dLat1 = pointList.get(iIndex).getLat();
                dLon2 = pointList.get(0).getLon();
                dLat2 = pointList.get(0).getLat();
            } else {
                dLon1 = pointList.get(iIndex).getLon();
                dLat1 = pointList.get(iIndex).getLat();
                dLon2 = pointList.get(iIndex + 1).getLon();
                dLat2 = pointList.get(iIndex + 1).getLat();
            }
            // 以下语句判断A点是否在边的两端点的水平平行线之间，在则可能有交点，开始判断交点是否在左射线上
            if (((lat >= dLat1) && (lat < dLat2)) || ((lat >= dLat2) && (lat < dLat1))) {
                if (Math.abs(dLat1 - dLat2) > 0) {
                    //得到 A点向左射线与边的交点的x坐标：
                    dLon = dLon1 - ((dLon1 - dLon2) * (dLat1 - lat)) / (dLat1 - dLat2);
                    // 如果交点在A点左侧（说明是做射线与 边的交点），则射线与边的全部交点数加一：
                    if (dLon < lon) {
                        iSum++;
                    }
                }
            }
        }
        return (iSum % 2) != 0;
    }
}

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
class PointBO {
    /**
     * 经度
     */
    private Double lon;
    /**
     * 纬度
     */
    private Double lat;
}
