import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { FlexLayoutModule } from "@angular/flex-layout";
import { RoutingModule } from "./routing/routing.module";
import { FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { AutoCompleteModule } from "primeng/primeng";
import { MessageModule } from "primeng/message";
import { FileUploadModule } from "primeng/primeng";
import { DropdownModule } from "primeng/primeng";
import { PanelModule} from "primeng/panel";
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from "primeng/button";
import { TooltipModule } from 'primeng/tooltip';
import { CheckboxModule } from 'primeng/checkbox';
import { MessagesModule } from 'primeng/messages';
import { MenubarModule } from 'primeng/menubar';
import { ToolbarModule } from 'primeng/toolbar';
import { TableModule } from 'primeng/table';
import { DialogModule } from "primeng/dialog";
import { DataViewModule } from 'primeng/dataview';
import { ConfirmDialogModule } from "primeng/primeng";
import { ToastModule } from 'primeng/toast';
import { ChipsModule } from 'primeng/chips';
import { PickListModule } from 'primeng/picklist';
import { PasswordModule } from 'primeng/password';
import { SliderModule } from 'primeng/slider';
import { ListboxModule } from 'primeng/listbox';
import { TabViewModule } from "primeng/primeng";
import { CarouselModule } from "primeng/primeng";
import { EditorModule } from 'primeng/editor';

import { AuthGuard } from "./auth.guard";
import { UserService } from "./service/user.service";
import { SongService } from "./service/song.service";
import { AlbumService } from "./service/album.service";
import { PlaylistService } from "./service/playlist.service";
import { AuthService } from "./service/auth.service";
import { SearchService } from "./service/search.service";
import { GenreService } from "./service/genre.service";

import { AppComponent } from './app.component';
import { MenuComponent } from './sites/menu/menu.component';
import { MainPageComponent } from './sites/main-page/main-page.component';
import { RegisterComponent } from './sites/user/register/register.component';
import { LoginComponent } from './sites/user/login/login.component';
import { UserDetailComponent } from './sites/user/user-detail/user-detail.component';
import { AlbumFormComponent } from './sites/album/album-form/album-form.component';
import { AlbumListComponent } from './sites/album/album-list/album-list.component';
import { AlbumDetailComponent } from './sites/album/album-detail/album-detail.component';
import { SongListComponent } from './sites/song/song-list/song-list.component';
import { SongDetailComponent } from './sites/song/song-detail/song-detail.component';
import { PlaylistFormComponent } from './sites/playlist/playlist-form/playlist-form.component';
import { PlaylistListComponent } from './sites/playlist/playlist-list/playlist-list.component';
import { PlaylistDetailComponent } from './sites/playlist/playlist-detail/playlist-detail.component';
import { CountryService } from "./service/country.service";

import { LOCALE_ID } from "@angular/core";
import { registerLocaleData } from "@angular/common";
import localeHu from '@angular/common/locales/hu';
import { AlbumSongsComponent } from './sites/album/album-songs/album-songs.component';
import { PlaylistSongsComponent } from './sites/playlist/playlist-song/playlist-song.component';
import { SearchTagComponent } from "./sites/search/search-tag/search-tag.component";
import { SearchGenreComponent } from './sites/search/search-genre/search-genre.component';
import { SearchWordComponent } from './sites/search/search-word/search-word.component';

registerLocaleData(localeHu);

@NgModule({
  declarations: [
    AppComponent,
    UserDetailComponent,
    LoginComponent,
    MainPageComponent,
    MenuComponent,
    AlbumListComponent,
    AlbumDetailComponent,
    PlaylistListComponent,
    PlaylistDetailComponent,
    SongDetailComponent,
    SongListComponent,
    RegisterComponent,
    AlbumFormComponent,
    PlaylistFormComponent,
    AlbumSongsComponent,
    PlaylistSongsComponent,
    SearchTagComponent,
    SearchGenreComponent,
    SearchWordComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    RoutingModule,
    FormsModule,
    HttpClientModule,
    AutoCompleteModule,
    MessageModule,
    FileUploadModule,
    DropdownModule,
    PanelModule,
    InputTextModule,
    ButtonModule,
    TooltipModule,
    CheckboxModule,
    MessagesModule,
    MenubarModule,
    ToolbarModule,
    TableModule,
    DialogModule,
    DataViewModule,
    ConfirmDialogModule,
    ToastModule,
    ChipsModule,
    PickListModule,
    PasswordModule,
    SliderModule,
    ListboxModule,
    TabViewModule,
    CarouselModule,
    EditorModule
  ],
  providers: [AuthService,
              UserService,
              AlbumService,
              SongService,
              PlaylistService,
              SearchService,
              GenreService,
              CountryService,
              AuthGuard,
              {provide: LOCALE_ID, useValue: "hu"}],
  bootstrap: [AppComponent]
})
export class AppModule { }
