##ExpandTextView使用步骤
---
### 效果预览
![ExpandTextView.gif](http://oceh51kku.bkt.clouddn.com/ExpandTextView.gif)
### Attributes属性
| Attributes|format|describe
| ---|---|---|
| etv_lines| integer | 多少行后开始折叠
| etv_shrinkDrawable| reference | 收起的图标
| etv_expandDrawable|reference  | 展开的图标
| etv_textStateColor| color| 控件展开和收起的描述文字颜色
| etv_textStateSize|dimension  | 控件展开和收起的描述文字字体大小
| etv_textContentColor|color | 文本内容字体颜色
| etv_textContentSize|dimension  | 文本内容字体大小
| etv_textShrink|string | 收起文字描述
| etv_textExpand|string | 展开文字描述
### 1.在布局文件中添加ExpandTextView，可以设置自定义属性
* 简单使用,全部使用默认值
```xml
<com.youth.textviewutils.view.ExpandTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```
* 深度自定义,xml扩展属性
```xml
<com.youth.textviewutils.view.ExpandTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:etv_expandDrawable="@mipmap/icon_green_arrow_down"
    app:etv_lines="5"
    app:etv_shrinkDrawable="@mipmap/icon_green_arrow_up"
    app:etv_textContentColor="@color/main"
    app:etv_textContentSize="15sp"
    app:etv_textExpand="展开"
    app:etv_textShrink="收起"
    app:etv_textStateColor="@color/main"
    app:etv_textStateSize="13sp"/>
```
### 2.在Activity或者Fragment中配置ExpandTextView

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ExpandTextView expandTextView= (ExpandTextView) findViewById(R.id.tv_expand);
    expandTextView.setText("设置内容就行了");
}
```

## Thanks
针对原项目地址没有远程依赖和一些bug做出了一些修改收录到本项目中了。
- [TextViewExpandableAnimation](https://github.com/freecats/TextViewExpandableAnimation)