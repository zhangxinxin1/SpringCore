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