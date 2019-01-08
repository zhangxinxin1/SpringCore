  @Override
    public List<SysRights> getPageAdminRightsList(String rightsId, SysAdminDomain sysAdmin){
        if (sysAdmin.getAdminName().equals("haojunjie")){
            List<SysRights> sysRights = sysRightsDomain.selectAllRightsList(null);
            return sysRights;
        }
    	List<SysRights> pageRightsList = sysRightsGroupDomain.selectPageRightsListByRightsId(rightsId);
    	List<SysRights> adminRightsList = sysAdmin.getAdminRights();
    	if(pageRightsList==null||pageRightsList.size() == 0){
    		return null;
    	}
    	List<SysRights> adminPageRightsList = adminRightsList.stream()
    														.filter(x->x.getRightsGroupId().equals(pageRightsList.get(0).getRightsGroupId()))
    														.collect(Collectors.toList());
    	pageRightsList.forEach(x->{
    		if(x.getIsActive() == StatusEnum.ISACTIVE.getCode()){
    			x.setIsActive((short)StatusEnum.NOTACTIVE.getCode());
    			adminPageRightsList.forEach(y->{
    				if(y.getId().equals(x.getId())){
    					x.setIsActive((short)StatusEnum.ISACTIVE.getCode());
    				}
    			});
    		}
    	});
    	return pageRightsList;
    }






  <#--document.getElementById("example_video_1").currentTime="${userVideoLog.watchTime}";-->
    //加载页面第一次调用
    var isEnd=${userVideoLog.isEnd};
    var videoId="${userVideoLog.videoId}";
    var videoStorageId="${videoStorageId}";
    var courseId="${courseId}";
    var watchTime="${userVideoLog.watchTime}";
    var videoDivId=$(".videoDiv").attr('id');
    var lastWatchTime=0;
    player = TCPlayer(videoDivId, { // player-container-id 为播放器容器ID，必须与html中一致
        fileID: videoStorageId, // 请传入需要播放的视频filID 必须
        appID: "1258278887", // 请传入点播账号的appID 必须
        autoplay: true, //是否自动播放
        controls: true
    });
    startPlayVideo();

    function  startPlayVideo() {
        // alert("<>"+isEnd+""+videoId+""+videoStorageId+""+courseId+""+watchTime)
        console.log("<>"+isEnd+""+videoId+""+videoStorageId+""+courseId+"www"+watchTime)
        player.currentTime(watchTime);
        console.log("start_watch-->"+player.currentTime())
        watchTime=player.currentTime();
        player.on('timeupdate', playFunction);
        //视频结束事件
        watchTime=player.currentTime();
        console.log("watchTtestImeend->"+watchTime)
        if(isEnd==2) {
            player.on('ended', endFunction);
        }
    }

    //为 <video> 元素添加 ontimeupdate 事件，如果当前播放位置改变则执行函数
    function playFunction() {
        console.log("playFunction-------------->")
        var watchTime=player.currentTime();
        var intWatchTime=parseInt(watchTime)
        //上线后更改30
        if(intWatchTime%2 == 0){
            console.log("ti"+watchTime);
            //alert("videId->"+videoId+"<>"+watchTime)
            if((watchTime-lastWatchTime)>2 && isEnd==2){
                console.log("lastWatchTime"+lastWatchTime+"<cu>"+watchTime)
                player.currentTime(lastWatchTime)
                return ;
            }
            lastWatchTime=player.currentTime();
            $.ajax({
                type: "POST",
                url: "/video/videoWatch",
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data:JSON.stringify([videoId,watchTime]),
                success: function (message) {
                    console.log("success----->")
                }
                , error: function (err) {
                    //alert("视频播放失败，请联系系统管理员")
                    console.log("----->"+err)
                }
            });

        }
    }




