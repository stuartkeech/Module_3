<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>   
<div id="main-body">
	<div id="login-information" style="float:right;">
		<p id="username">UserName</p>
		<p id="role">Role</p>
		<p id="dateTime">Date & Time</p>
		<p id="weatherLocation">Current Weather</p>
		<p id="weatherTemperature">Current Weather</p>
		<p id="weatherTemperatureC">Current Weather</p>
		<p id="weatherHumidity">Current Weather</p>
	</div>

	<div id="widgetmain"
		style="text-align: left; overflow-y: auto; overflow-x: hidden; width: 200px; background-color: #transparent; border: 1px solid #333333;">
		<div id="rsswidget" style="height: 500px;">
			<iframe
				src="http://us1.rssfeedwidget.com/getrss.php?time=1534792576078&amp;x=https%3A%2F%2Fwww.ctvnews.ca%2Frss%2Fautos%2Fctv-news-autos-1.867636&amp;w=200&amp;h=500&amp;bc=333333&amp;bw=1&amp;bgc=transparent&amp;m=20&amp;it=true&amp;t=(default)&amp;tc=333333&amp;ts=15&amp;tb=transparent&amp;il=true&amp;lc=0000FF&amp;ls=14&amp;lb=false&amp;id=true&amp;dc=333333&amp;ds=14&amp;idt=true&amp;dtc=284F2D&amp;dts=12"
				border="0" hspace="0" vspace="0" frameborder="no" marginwidth="0"
				marginheight="0"
				style="border: 0; padding: 0; margin: 0; width: 200px; height: 500px;"
				id="rssOutput"> Reading RSS Feed ...</iframe>
		</div>
		<div
			style="text-align: right; margin-bottom: 0; border-top: 1px solid #333333;"
			id="widgetbottom">
			<span style="font-size: 70%"><a
				href="http://www.rssfeedwidget.com">rss feed widget</a>&nbsp;</span><br>
		</div>
	</div>	
</div>		
			Please put all the content/Information here

</t:genericpage>

<script>
	var d = new Date();
	document.getElementById("dateTime").innerHTML = d;
	
	var callbackFunction = function(data) {
	    var weather = data.query.results.channel;
	    
	    $('#weatherLocation').text("Weather " + weather.item.title);
	    $('#weatherTemperature').text("Temperature in Fahrenheit is " + weather.item.condition.temp);
	    $('#weatherTemperatureC').text("Temperature in Celsius is " + (Math.round((weather.item.condition.temp-32)*(5/9))) );
	    
	    $('#weatherHumidity').text("Humidity is " + weather.atmosphere.humidity);
	    
	  };
	
	
	 
	</script>
<script src="https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where woeid in (select woeid from geo.places(1) where text='toronto, on')&format=json&callback=callbackFunction"></script>
