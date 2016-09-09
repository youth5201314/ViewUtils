##TextViewStyle使用步骤
---
### 效果预览
![JustifyTextView.png](http://oceh51kku.bkt.clouddn.com/JustifyTextView.png)
### Attributes属性
|Attributes|format|describe
| --- | --- | --- |
| lineSpacing | dimension | 行间距

### 在布局文件中添加JustifyTextView，可以设置自定义属性

```xml
<com.youth.textviewutils.view.JustifyTextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:lineSpacing="5dp"
    android:text="内容"/>
```

