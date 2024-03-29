<div id="top5Panel" class="top5Panel">
    <ul id="tabs" class="tabs clearfix">
        <li class="selected"><a href="#albums">Latest Albums</a></li>
        <li><a href="#songs">Latest Songs</a></li>
        <li><a href="#artists">Newest Artists</a></li>
    </ul>
    <div id="albums" class="top5Item">
        <g:render
            template="/album/albumList"
            model="[albums: top5Albums]">
        </g:render>
    </div>
    <div id="songs" class="top5Item hide">
        <g:render
            template="/song/songList"
            model="[songs: top5Songs]">
        </g:render>
    </div>
    <div id="artists" class="top5Item list hide">
        <g:render
            template="/artist/artistList"
            model="[artists: top5Artists]">
        </g:render>
    </div>
</div>