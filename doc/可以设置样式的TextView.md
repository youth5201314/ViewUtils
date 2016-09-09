##TextViewStyle使用步骤
---
### 效果预览
![Text_View_Style.png](http://oceh51kku.bkt.clouddn.com/Text_View_Style.png)
### Attributes属性
|Attributes|format|describe
| --- | --- | --- |
| tvs_backgroundColor| color/reference | 背景色
| tvs_backgroundRadius| dimension | 圆角弧度，默认0

### 在布局文件中添加TextViewStyle，可以设置自定义属性

```xml
<com.youth.textviewutils.view.TextViewStyle
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="内容"
    android:textColor="@android:color/white"
    app:tvs_backgroundColor="@color/gold"
    app:tvs_backgroundRadius="4"/>
```

