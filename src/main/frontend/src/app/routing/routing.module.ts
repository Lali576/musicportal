import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";

import { MainPageComponent } from "../sites/main-page/main-page.component";
import { AlbumListComponent } from "../sites/album/album-list/album-list.component";
import { AuthGuard } from "../auth.guard";
import { AlbumDetailComponent } from "../sites/album/album-detail/album-detail.component";
import { SongListComponent } from "../sites/song/song-list/song-list.component";
import { SongDetailComponent } from "../sites/song/song-detail/song-detail.component";
import { PlaylistListComponent } from "../sites/playlist/playlist-list/playlist-list.component";
import { PlaylistDetailComponent } from "../sites/playlist/playlist-detail/playlist-detail.component";
import { RegisterComponent } from "../sites/user/register/register.component";
import { LoginComponent } from "../sites/user/login/login.component";
import { UserDetailComponent } from "../sites/user/user-detail/user-detail.component";
import { SearchComponent } from "../sites/search/search.component";
import {AlbumFormComponent} from "../sites/album/album-form/album-form.component";
import {PlaylistFormComponent} from "../sites/playlist/playlist-form/playlist-form.component";
import {AlbumSongsComponent} from "../sites/album/album-songs/album-songs.component";
import {PlaylistSongsComponent} from "../sites/playlist/playlist-song/playlist-song.component";

const routes: Routes = [
  {
    path: '',
    component: MainPageComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'user/:id',
    component: UserDetailComponent
  },
  {
    path: 'album/list',
    component: AlbumListComponent
  },
  {
    path: 'album/new',
    component: AlbumFormComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST']}
  },
  {
    path: 'album/:id',
    component: AlbumDetailComponent
  },
  {
    path: 'album/:id/songs',
    component: AlbumSongsComponent
  },
  {
    path: 'song/list',
    component: SongListComponent
  },
  {
    path: 'song/:id',
    component: SongDetailComponent
  },
  {
    path: 'playlist/list',
    component: PlaylistListComponent
  },
  {
    path: 'playlist/new',
    component: PlaylistFormComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST', 'USER']}
  },
  {
    path: 'playlist/:id',
    component: PlaylistDetailComponent
  },
  {
    path: 'playlist/:id/songs',
    component: PlaylistSongsComponent
  },
  {
    path: 'search/:word',
    component: SearchComponent,
  }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  declarations: []
})
export class RoutingModule { }
