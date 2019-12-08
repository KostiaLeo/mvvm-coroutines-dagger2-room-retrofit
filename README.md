# Music news
<h3>Application which retrieves data from Webserver (via Retrofit), saves it into SQLite and get from local database (SQLite) if user is offline. <br/>
There is applying MVVM architecture pattern example. </h3>
<h3>Overview: </h3>
<ul>
<li><h3>Model</h3>
Model is implemented as Repository pattern. Firstly it begins from internet connection checking. Consequently if it's alive we're retrieving data from the server (by using Retrofit 2) and inserting into the SQLite database. Otherwise we're trying to fetch data from the SQLite itself.
</li>
<li><h3>View</h3>
View is realised as 2 fragments. First one contains RecyclerView, second one depends on clicks on recycler-items and finally displays detailed data fetched from the Model.
It implements state saving reflected on configuration changes.
</li>
<li><h3>ViewModel</h3>
VM is responsible for transferring data between view and model.
</li>
</ul>
<hr/>

<h3> Applied technologies and libraries: </h3>
<ul>
<li><h3>Model</h3>

<h4><i>Retrofit 2</i></h4> - getting data from server into pojo-classes
<h4><i>SQLite</i></h4> - storing data fetched from server. User get data here if he is offline.
<h4><i>Coroutines</i></h4> - managing database queries. 
<h4><i>RxJava (RxKotlin)</i></h4>
   - converting data from server to Observable. 
   - communication between fragments, click listeners.
</li>	 
<li><h3>ViewModel</h3>
<h4><i>LiveData</i></h4> - live changing data into fetched one from model
</li>

<li><h3>View</h3>
<h4><i>Fragments</i></h4> - interactive displaying and click reflecting
<h4><i>Data Binding</i></h4>
   - replace basic operations with UI (e.g. findViewById() ) to the XML.
</li>
</ul>
