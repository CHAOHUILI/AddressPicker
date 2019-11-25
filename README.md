# AddressPicker
地址选择器，三级联动
效果图：

![effect image](https://github.com/CHAOHUILI/AddressPicker/blob/master/addressPicker.png)


使用方法：

在你projects的根目录的build.gradle中添加:

    allprojects {
      repositories {
          jcenter()
          maven { url "https://jitpack.io"}
      }
    }

然后在你moudule的build.gradle添加依赖:

	dependencies {
	        compile 'com.github.CHAOHUILI:AddressPicker:v1.1.0'
	}
  
  具体使用（使用方法和此代码库中MainActivity方法相同，可下载参考）：
  
  首先初始化地址数据：
  
            InitAreaTask initAreaTask = new InitAreaTask(MainActivity.this, provinces);
            initAreaTask.execute(0);
            initAreaTask.setOnLoadingAddressListener(MainActivity.this);
            
  其中provinces是一个全局Province数组，；
            
  然后使当前activity或者fragment继承： InitAreaTask.onLoadingAddressListener（或者new一个，自己注册监听），实现其中的onLoadFinished（代表初始化   地址完成）方法和 onLoading（代表开始初始化）方法。可以在onLoadFinished后显示地址选择框，代码：
  
  
      @Override
      public void onLoadFinished() {
          showAddressDialog();
      }
      private void showAddressDialog() {
           new CityPickerDialog(MainActivity.this, provinces, 
	         null, null, null, new CityPickerDialog.onCityPickedListener() {
                    @Override
                    public void onPicked(Province selectProvince,
                                         City selectCity, County selectCounty) {
                        StringBuilder address = new StringBuilder();
                        address.append(
                                selectProvince != null ? selectProvince
                                        .getAreaName() : "")
                                .append(selectCity != null ? selectCity
                                        .getAreaName() : "");
                        if (selectCounty != null) {
                            String areaName = selectCounty.getAreaName();
                            if (areaName != null) {
                                address.append(areaName);
                            }
                        }
                        Toast.makeText(MainActivity.this,address.toString(),Toast.LENGTH_SHORT).show();
                    }
                }).show();
      }
    
 点击完成，就会弹出选择地址的提示。
    
 您也可以把下载下来，当做项目的module进行依赖，这样可以进行简单的修改，例如文字的颜色和弹框的布局。
    
   如果使用方便，记得star哦！
