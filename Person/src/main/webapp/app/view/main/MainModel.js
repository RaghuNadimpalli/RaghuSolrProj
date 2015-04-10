/**
* This class is the view model for the Main view of the application. 
*/
Ext.define('MyApp.view.main.MainModel', {
	extend: 'Ext.app.ViewModel',
	alias: 'viewmodel.main',
	data: {
		gridTitle : '',
		lastUpdated : '',
		dateRangeString : '',
		searchString : '',
		headerHTML : '<nav role="navigation" class="navbar navbar-barclays">'+
				'<div class="navbar-header">'+
				'<button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">'+
					'<span class="sr-only">Toggle navigation</span>'+
					'<span class="icon-bar"></span>'+
					'<span class="icon-bar"></span>'+
					'<span class="icon-bar"></span>'+
				'</button>'+
				'<a class="navbar-brand header_logo" href="#">'+
					'<img src="resoueces/images/header/barclays_logo.gif" alt="">'+
				'</a>'+
				'<hr>'+
			'</div>'+
			
			'<div id="navbarCollapse" class="collapse navbar-collapse">'+
				'<ul class="nav navbar-nav">'+
					'<li><a class="large_header_text" href="#">| Person Application </a></li>'+
				'</ul>'+
				
				'<ul class="nav navbar-nav navbar-right">'+
					'<li><a class="small_header_text" href="logout.jsp"><b>Welcome User | Logout</b></a></li>'+
				'</ul>'+
			'</div>'+
			'</nav>'+
			'<br></br>'+
			'<hr>'
	}
});