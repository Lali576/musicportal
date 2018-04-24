import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { UserEditComponent } from './user-edit/user-edit.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { LoginComponent } from './login/login.component';
import { MainPageComponent } from './main-page/main-page.component';
import { MenuComponent } from './menu/menu.component';
import { AlbumEditComponent } from './album-edit/album-edit.component';
import { AlbumListComponent } from './album-list/album-list.component';
import { AlbumDetailComponent } from './album-detail/album-detail.component';
import { PlaylistListComponent } from './playlist-list/playlist-list.component';
import { PlaylistEditComponent } from './playlist-edit/playlist-edit.component';
import { PlaylistDetailComponent } from './playlist-detail/playlist-detail.component';
import { SongEditComponent } from './song-edit/song-edit.component';
import { SongDetailComponent } from './song-detail/song-detail.component';
import { SongListComponent } from './song-list/song-list.component';


@NgModule({
  declarations: [
    AppComponent,
    UserEditComponent,
    UserDetailComponent,
    LoginComponent,
    MainPageComponent,
    MenuComponent,
    AlbumEditComponent,
    AlbumListComponent,
    AlbumDetailComponent,
    PlaylistListComponent,
    PlaylistEditComponent,
    PlaylistDetailComponent,
    SongEditComponent,
    SongDetailComponent,
    SongListComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
