 if(list !=null && list.size>0){};
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


 public static void main(String[] args){
       //big_1用来标记一个数组中的最大值
        int big_1 = arr[0];
        //big_2用来标记一个数组中的次大值
        int big_2 =Integer.MIN_VALUE;
        for(int i=1;i<arr.length;i++) {
            if(arr[i]>big_1) {
                big_2 = big_1;
                big_1 = arr[i];

            }else if(arr[i]>big_2 && arr[i]!=big_1){
                big_2 = arr[i];
            }
        }
        System.out.println("数组中最大的数是："+big_1+"第二大的数是："+big_2);

    }



package com.knys.edusvc.webmanage.uservideo;

public class SingleLinkedList {

    /**
     * 链表的头节点
     */

    private  Node head ;
    private  int length = 0;
    private int pos = 0;// 节点的位置
    /**
     * 获取链表的长度
     */
    public  int length(){
        return length;
    }
    // 在任意位置插入节点 在index的后面插入
    public void add(int index, int data) {
        Node node = new Node(data);
        Node current = head;
        Node previous =  head;
        while (pos != index) {
            previous = current;
            current = current.next;
            pos++;
        }
        node.next = current;
        previous.next = node;
        pos = 0;
    }


    /**
     * 尾插法增加结点操作
     * @param data
     */
    public  void addLastNode(int data){
        Node tNode = new Node(data);
        if (head == null){
            head = tNode;
            length++;
            return;
        }
        Node temp = head;
        while (temp.next != null){
            temp = temp.next;
        }
        temp.next = tNode;
        length++;
    }

    /**
     * 头插法增加结点的操作
     * @param data
     */
    public  void addHeadNode(int data){
        Node tNode = new Node(data);
        if (head == null){
            head = tNode;
            length++;
            return;
        }
        Node temp = head;
        tNode.next = temp;
        head = tNode;
        length++;
    }


    /**
     * 插入结点到链表的指定位置
     * @param index
     * @param data
     */
    public  void insertNodeByIndex(int index,int data){

        //先判断指定位置是否合法
        if (index < 1 || index > length+1){
            try {
                throw new MyException("指定位置不合法，插入结点失败");
            } catch (MyException e) {
                e.printStackTrace();
            }
        }
        if (index == 1){
            addHeadNode(data);
            return;
        }
        if (index == length+1){
            addLastNode(data);
            return;
        }

        Node temp = head;
        Node tNode = new Node(data);
        int tlength = 1;
        while (temp.next != null){
            if (index == ++tlength){
                //当前结点向后移动一位
                tNode.next = temp.next;
                temp.next = tNode;
                length++;
                return;
            }
            temp = temp.next;
        }

    }


    /**
     * 删除指定位置的结点
     * @param index
     */
    public  void delNodeByIndex(int index){
        //先判断指定索引合不合法
        if (index < 1 || index > length){
            try {
                throw new MyException("指定位置参数不合法");
            } catch (MyException e) {
                e.printStackTrace();
            }
        }
        Node temp = head;
        int tlength = 1;
        while (temp.next != null){
            //如果到达了指定结点则进行删除
            if (index == ++tlength){
                temp.next = temp.next.next;
                length--;
                return;
            }
            temp = temp.next;
        }
    }

    /**
     * 删除链表重复结点
     * @return
     */
    public boolean deleteRepeatNode(){
        Node temp = head;
        while (temp != null){
            Node tNode = head;
            while (tNode.next != temp && tNode.next != null){
                if (tNode.next.data == temp.data){
                    tNode.next = tNode.next.next;
                }else {
                    tNode = tNode.next;
                }
            }
            temp = temp.next;
        }
        return true;
    }

    /**
     * 遍历单链表，打印链表中的值
     */
    public  void printData(){
        Node temp = head;
        System.out.print(temp.data);
        while (temp.next != null){
            System.out.print(","+temp.next.data);
            temp = temp.next;
        }
        System.out.println(",长度："+length());
    }

    /**
     * 修改指定结点的值
     * @param index
     * @param data
     */
    public boolean updateNode(int index,int data){
        //判断指定位置是否存在结点
        if (index < 0 || index > length){
            try {
                throw new MyException("不存在该指定结点，修改值失败");
            } catch (MyException e) {
                e.printStackTrace();
            }
        }
        Node temp = head;
        int i=1;
        while(index != i){
            temp = temp.next;
            i++;
        }
        temp.data = data;
        return true;

    }

    /**
     * 单链表的选择排序---正序
     * 注意排序的只是data而不是结点本身,不要混淆！！
     * @return
     */
    public boolean selectedSortNode(){

        Node temp = head;

        while (temp != null){
            Node tNode = temp.next;
            while (tNode != null){
                //判断大小交换data
                if (temp.data > tNode.data){
                    int da = tNode.data;
                    tNode.data = temp.data;
                    temp.data = da;
                }
                tNode = tNode.next;
            }
            temp = temp.next;
        }
        return true;
    }

    /**
     * 冒泡排序法排序----倒序
     * @return
     */
    public boolean bubbleSortNode(){

        Node temp = head;
        int len_temp = 1;
        while (temp != null){
            Node tNode = temp.next;
            int len_tNode = 1;
            if (len_tNode++ < length-len_temp){
                while (tNode != null){
                    if (temp.data < tNode.data){
                        int da = temp.data;
                        temp.data = tNode.data;
                        tNode.data = da;
                    }
                    tNode = tNode.next;
                }
            }
            temp = temp.next;
            len_temp++;
        }

        return true;
    }


    /**
     * 反转排序，将第一个和倒数第一个位置交换，以此类推
     * @return
     */
    public boolean reverseSortNode(){
        Node temp = head;
        for (int i = 1; i<=length/2; i++){
            //找到倒数第i个结点
            Node q = head;
            int len = 1;
            while (q != null && len != length-i+1){
                len++;
                q = q.next;
            }
            int da = q.data;
            q.data = temp.data;
            temp.data = da;
            temp = temp.next;
        }
        return true;
    }



    public  boolean isEmpty(){
        if (head == null){
            return true;
        }
        return false;
    }


    public static void main(String[] args){
        SingleLinkedList linkedList = new SingleLinkedList();
        linkedList.addLastNode(5);
        linkedList.addLastNode(21);
        linkedList.addLastNode(22);
        linkedList.addLastNode(9);
        linkedList.addLastNode(7);
        System.out.println(linkedList.length());
        linkedList.printData();
        System.out.println(linkedList.isEmpty());
        linkedList.addHeadNode(78);
        linkedList.addHeadNode(101);
        System.out.println("*************************************");
        linkedList.printData();
        System.out.println(linkedList.isEmpty());
        System.out.println("*************************************");
        linkedList.insertNodeByIndex(8,222);
        linkedList.printData();
        System.out.println("*************************************");
        linkedList.insertNodeByIndex(2,600);
        linkedList.insertNodeByIndex(3,68);
        linkedList.add(4,188);
        linkedList.printData();
        // linkedList.delNodeByIndex(10);
        linkedList.updateNode(2,1000);
        linkedList.selectedSortNode();
        linkedList.printData();
        linkedList.bubbleSortNode();
        linkedList.printData();
        linkedList.reverseSortNode();
        linkedList.printData();
        linkedList.addLastNode(22);
        linkedList.printData();
        linkedList.deleteRepeatNode();
        linkedList.printData();
    }

}




class MyException extends Exception{

    private String errorMessage;

    public MyException(String message) {
        super(message);
        this.errorMessage = message;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}


class Node {

    /**
     * 包含两个属性一个是结点数据，和下一个结点的引用
     */
    public Node next;
    public int data;

    /**
     * constructor
     */
    public Node(int data) {
        this.data = data;
    }
}





 static int r=0;
    static int ord(int n){
        if(n<10){
            System.out.println("<>"+n);
            r=r*10+n;

            return r;
        }
        r=r*10+(n%10);
        ord(n/10);
        System.out.println("<qqq>"+r);
        return r;

    }

    public static void main(String[] args){
        int ord = ord(2430007);
        System.out.println("<>"+ord);

        int res=0;
        int n=907;
        while (n!=0){
            res=(res*10)+(n%10);
            n=n/10;
        }
        System.out.println("<res------->"+res);

        int qqq=3234232;
        int k=0;
        while (qqq>0){
            k++;
            qqq=qqq/10;
            System.out.println("<outqq>"+qqq);
        }
        System.out.println("<out>"+k);

    }














