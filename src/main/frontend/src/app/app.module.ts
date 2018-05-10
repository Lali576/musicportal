import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FlexLayoutModule} from "@angular/flex-layout";
import {RoutingModule} from "./routing/routing.module";
import {MatToolbarModule, MatButtonModule, MatMenuModule, MatIconModule,
        MatFormFieldModule, MatInputModule, MatButtonToggleModule,
        MatSelectModule, MatOptionModule, MatCheckboxModule,
        MatDatepickerModule, MatNativeDateModule} from "@angular/material";

import { AppComponent } from './app.component';
import { UserEditComponent } from './sites/user/user-edit/user-edit.component';
import { UserDetailComponent } from './sites/user/user-detail/user-detail.component';
import { LoginComponent } from './sites/user/login/login.component';
import { MainPageComponent } from './sites/main-page/main-page.component';
import { MenuComponent } from './sites/menu/menu.component';
import { AlbumEditComponent } from './sites/album/album-edit/album-edit.component';
import { AlbumListComponent } from './sites/album/album-list/album-list.component';
import { AlbumDetailComponent } from './sites/album/album-detail/album-detail.component';
import { PlaylistListComponent } from './sites/playlist/playlist-list/playlist-list.component';
import { PlaylistEditComponent } from './sites/playlist/playlist-edit/playlist-edit.component';
import { PlaylistDetailComponent } from './sites/playlist/playlist-detail/playlist-detail.component';
import { SongEditComponent } from './sites/song/song-edit/song-edit.component';
import { SongDetailComponent } from './sites/song/song-detail/song-detail.component';
import { SongListComponent } from './sites/song/song-list/song-list.component';
import {UserService} from "./service/user.service";
import {SongService} from "./service/song.service";
import {AlbumService} from "./service/album.service";
import {PlaylistService} from "./service/playlist.service";
import { RegisterComponent } from './sites/user/register/register.component';
import { SongCommentComponent } from './sites/song/song-comment/song-comment.component';
import { UserMessageComponent } from './sites/user/user-message/user-message.component';
import {AuthService} from "./auth.service";
import {AuthGuard} from "./auth.guard";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { SearchComponent } from './sites/search/search.component';
import { SearchAlbumComponent } from './sites/search/search-album/search-album.component';
import { SearchSongComponent } from './sites/search/search-song/search-song.component';
import { SearchPlaylistComponent } from './sites/search/search-playlist/search-playlist.component';
import { AlbumFormComponent } from './sites/album/album-form/album-form.component';
import { PlaylistFormComponent } from './sites/playlist/playlist-form/playlist-form.component';


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
    SongListComponent,
    RegisterComponent,
    SongCommentComponent,
    UserMessageComponent,
    SearchComponent,
    SearchAlbumComponent,
    SearchSongComponent,
    SearchPlaylistComponent,
    AlbumFormComponent,
    PlaylistFormComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatMenuModule,
    MatInputModule,
    MatIconModule,
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    RoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [UserService,
              SongService,
              AlbumService,
              PlaylistService,
              AuthService,
              AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
