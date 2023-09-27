using System;
using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace FitHub.Web.Migrations
{
    //20230926112654_init
    /// <inheritdoc />
    public partial class init : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
             name: "images",
             columns: table => new
             {
                 imageid = table.Column<int>(type: "integer", nullable: false)
                     .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                 imagedata = table.Column<byte[]>(type: "bytea", nullable: true),
                 imagetype = table.Column<string>(type: "character varying(255)", maxLength: 255, nullable: true),
                 userid = table.Column<string>(type: "character varying(450)", nullable: true),
                 postid = table.Column<int>(type: "integer", nullable: true),
                 creationdate = table.Column<DateTime>(type: "timestamp without time zone", nullable: true)
             },
             constraints: table =>
             {
                 table.PrimaryKey("images_pkey", x => x.imageid);
             });

            migrationBuilder.CreateTable(
                name: "users",
                columns: table => new
                {
                    Id = table.Column<string>(type: "character varying(450)", nullable: false),
                    firstname = table.Column<string>(type: "character varying(255)", maxLength: 255, nullable: true),
                    lastname = table.Column<string>(type: "character varying(255)", maxLength: 255, nullable: true),
                    profilepictureid = table.Column<int>(type: "integer", nullable: true),
                    bio = table.Column<string>(type: "text", nullable: true),
                    location = table.Column<string>(type: "character varying(255)", maxLength: 255, nullable: true),
                    registrationdate = table.Column<DateTime>(type: "timestamp without time zone", nullable: true),
                    birthdate = table.Column<DateOnly>(type: "date", nullable: true),
                    UserName = table.Column<string>(type: "character varying(450)", nullable: true),
                    NormalizedUserName = table.Column<string>(type: "character varying(450)", nullable: true),
                    Email = table.Column<string>(type: "character varying(255)", nullable: true),
                    NormalizedEmail = table.Column<string>(type: "character varying(255)", nullable: true),
                    EmailConfirmed = table.Column<bool>(type: "boolean", nullable: false),
                    PasswordHash = table.Column<string>(type: "text", nullable: true),
                    SecurityStamp = table.Column<string>(type: "text", nullable: true),
                    ConcurrencyStamp = table.Column<string>(type: "text", nullable: true),
                    PhoneNumber = table.Column<string>(type: "text", nullable: true),
                    PhoneNumberConfirmed = table.Column<bool>(type: "boolean", nullable: false),
                    TwoFactorEnabled = table.Column<bool>(type: "boolean", nullable: false),
                    LockoutEnd = table.Column<DateTimeOffset>(type: "timestamp with time zone", nullable: true),
                    LockoutEnabled = table.Column<bool>(type: "boolean", nullable: false),
                    AccessFailedCount = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("users_pkey", x => x.Id);
                    table.ForeignKey(
                        name: "fk_users_profile_picture",
                        column: x => x.profilepictureid,
                        principalTable: "images",
                        principalColumn: "imageid");
                });

            migrationBuilder.CreateTable(
                name: "chattypes",
                columns: table => new
                {
                    chattypeid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    name = table.Column<string>(type: "character varying(255)", maxLength: 255, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("chattypes_pkey", x => x.chattypeid);
                });

            migrationBuilder.CreateTable(
                name: "exercises",
                columns: table => new
                {
                    exerciseid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    name = table.Column<string>(type: "character varying(255)", maxLength: 255, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("exercises_pkey", x => x.exerciseid);
                });

            migrationBuilder.CreateTable(
                name: "Roles",
                columns: table => new
                {
                    Id = table.Column<string>(type: "character varying(450)", nullable: false),
                    Name = table.Column<string>(type: "character varying(255)", nullable: true),
                    NormalizedName = table.Column<string>(type: "character varying(255)", nullable: true),
                    ConcurrencyStamp = table.Column<string>(type: "text", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Roles", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "RoleClaims",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    RoleId = table.Column<string>(type: "character varying(450)", nullable: true),
                    ClaimType = table.Column<string>(type: "text", nullable: true),
                    ClaimValue = table.Column<string>(type: "text", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_RoleClaims", x => x.Id);
                    table.ForeignKey(
                        name: "FK_RoleClaims_Roles_RoleId",
                        column: x => x.RoleId,
                        principalTable: "Roles",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "sporttype",
                columns: table => new
                {
                    sporttypeid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    name = table.Column<string>(type: "character varying(255)", maxLength: 255, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("sporttype_pkey", x => x.sporttypeid);
                });

            migrationBuilder.CreateTable(
                name: "status",
                columns: table => new
                {
                    statusid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    name = table.Column<string>(type: "character varying(255)", maxLength: 255, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("status_pkey", x => x.statusid);
                });

            migrationBuilder.CreateTable(
                name: "UserClaims",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    UserId = table.Column<string>(type: "character varying(450)", nullable: true),
                    ClaimType = table.Column<string>(type: "text", nullable: true),
                    ClaimValue = table.Column<string>(type: "text", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_UserClaims", x => x.Id);
                    table.ForeignKey(
                        name: "FK_UserClaims_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "UserLogins",
                columns: table => new
                {
                    LoginProvider = table.Column<string>(type: "character varying(450)", nullable: false),
                    ProviderKey = table.Column<string>(type: "character varying(450)", nullable: false),
                    ProviderDisplayName = table.Column<string>(type: "text", nullable: true),
                    UserId = table.Column<string>(type: "character varying(450)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_UserLogins", x => new { x.LoginProvider, x.ProviderKey });
                    table.ForeignKey(
                        name: "FK_UserLogins_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "UserRoles",
                columns: table => new
                {
                    UserId = table.Column<string>(type: "character varying(450)", nullable: false),
                    RoleId = table.Column<string>(type: "character varying(450)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_UserRoles", x => new { x.UserId, x.RoleId });
                    table.ForeignKey(
                        name: "FK_UserRoles_Roles_RoleId",
                        column: x => x.RoleId,
                        principalTable: "Roles",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_UserRoles_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "UserTokens",
                columns: table => new
                {
                    UserId = table.Column<string>(type: "character varying(450)", nullable: false),
                    LoginProvider = table.Column<string>(type: "character varying(450)", nullable: false),
                    Name = table.Column<string>(type: "character varying(450)", nullable: false),
                    Value = table.Column<string>(type: "text", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_UserTokens", x => new { x.UserId, x.LoginProvider, x.Name });
                    table.ForeignKey(
                        name: "FK_UserTokens_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "chats",
                columns: table => new
                {
                    chatid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    chattypeid = table.Column<int>(type: "integer", nullable: true),
                    name = table.Column<string>(type: "character varying(255)", maxLength: 255, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("chats_pkey", x => x.chatid);
                    table.ForeignKey(
                        name: "chats_chattypeid_fkey",
                        column: x => x.chattypeid,
                        principalTable: "chattypes",
                        principalColumn: "chattypeid");
                });

            migrationBuilder.CreateTable(
                name: "exerciseinfo",
                columns: table => new
                {
                    exerciseinfoid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    exerciseid = table.Column<int>(type: "integer", nullable: true),
                    sets = table.Column<int>(type: "integer", nullable: true),
                    reps = table.Column<int>(type: "integer", nullable: true),
                    weightload = table.Column<double>(type: "double precision", nullable: true),
                    leadtime = table.Column<TimeSpan>(type: "interval", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("exerciseinfo_pkey", x => x.exerciseinfoid);
                    table.ForeignKey(
                        name: "exerciseinfo_exerciseid_fkey",
                        column: x => x.exerciseid,
                        principalTable: "exercises",
                        principalColumn: "exerciseid");
                });

            migrationBuilder.CreateTable(
                name: "chatmessages",
                columns: table => new
                {
                    messageid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    chatid = table.Column<int>(type: "integer", nullable: false),
                    userid = table.Column<string>(type: "character varying(450)", nullable: false),
                    content = table.Column<string>(type: "text", nullable: false),
                    timestamp = table.Column<DateTime>(type: "timestamp without time zone", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("chatmessages_pkey", x => x.messageid);
                    table.ForeignKey(
                        name: "chatmessages_chatid_fkey",
                        column: x => x.chatid,
                        principalTable: "chats",
                        principalColumn: "chatid",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "chatparticipants",
                columns: table => new
                {
                    participantid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    chatid = table.Column<int>(type: "integer", nullable: false),
                    userid = table.Column<string>(type: "character varying(450)", nullable: false),
                    ismoderator = table.Column<bool>(type: "boolean", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("chatparticipants_pkey", x => x.participantid);
                    table.ForeignKey(
                        name: "chatparticipants_chatid_fkey",
                        column: x => x.chatid,
                        principalTable: "chats",
                        principalColumn: "chatid",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "comments",
                columns: table => new
                {
                    commentid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    postid = table.Column<int>(type: "integer", nullable: false),
                    userid = table.Column<string>(type: "character varying(450)", nullable: false),
                    content = table.Column<string>(type: "text", nullable: true),
                    creationdate = table.Column<DateTime>(type: "timestamp without time zone", nullable: true),
                    statusid = table.Column<int>(type: "integer", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("comments_pkey", x => x.commentid);
                    table.ForeignKey(
                        name: "comments_statusid_fkey",
                        column: x => x.statusid,
                        principalTable: "status",
                        principalColumn: "statusid");
                });

            migrationBuilder.CreateTable(
                name: "diaries",
                columns: table => new
                {
                    diaryid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    creationdate = table.Column<DateTime>(type: "timestamp without time zone", nullable: true),
                    creatorid = table.Column<string>(type: "text", nullable: false),
                    lastmodifieddate = table.Column<DateTime>(type: "timestamp without time zone", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("diaries_pkey", x => x.diaryid);
                });

            migrationBuilder.CreateTable(
                name: "diaryexercise",
                columns: table => new
                {
                    diaryexerciseid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    diaryid = table.Column<int>(type: "integer", nullable: true),
                    exerciseinfoid = table.Column<int>(type: "integer", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("diaryexercise_pkey", x => x.diaryexerciseid);
                    table.ForeignKey(
                        name: "diaryexercise_diaryid_fkey",
                        column: x => x.diaryid,
                        principalTable: "diaries",
                        principalColumn: "diaryid");
                    table.ForeignKey(
                        name: "diaryexercise_exerciseinfoid_fkey",
                        column: x => x.exerciseinfoid,
                        principalTable: "exerciseinfo",
                        principalColumn: "exerciseinfoid");
                });


            migrationBuilder.CreateTable(
                name: "posts",
                columns: table => new
                {
                    postid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    userid = table.Column<string>(type: "character varying(450)", nullable: false),
                    title = table.Column<string>(type: "character varying(255)", maxLength: 255, nullable: true),
                    titleimageid = table.Column<int>(type: "integer", nullable: true),
                    content = table.Column<string>(type: "text", nullable: true),
                    creationdate = table.Column<DateTime>(type: "timestamp without time zone", nullable: true),
                    statusid = table.Column<int>(type: "integer", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("posts_pkey", x => x.postid);
                    table.ForeignKey(
                        name: "posts_statusid_fkey",
                        column: x => x.statusid,
                        principalTable: "status",
                        principalColumn: "statusid");
                    table.ForeignKey(
                        name: "posts_titleimageid_fkey",
                        column: x => x.titleimageid,
                        principalTable: "images",
                        principalColumn: "imageid");
                    table.ForeignKey(
                        name: "posts_userid_fkey",
                        column: x => x.userid,
                        principalTable: "users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "usersports",
                columns: table => new
                {
                    usersportid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    userid = table.Column<string>(type: "character varying(450)", nullable: false),
                    sporttypeid = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("usersports_pkey", x => x.usersportid);
                    table.ForeignKey(
                        name: "usersports_sporttypeid_fkey",
                        column: x => x.sporttypeid,
                        principalTable: "sporttype",
                        principalColumn: "sporttypeid",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "usersports_userid_fkey",
                        column: x => x.userid,
                        principalTable: "users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "workoutplans",
                columns: table => new
                {
                    planid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    creationdate = table.Column<DateTime>(type: "timestamp without time zone", nullable: true),
                    name = table.Column<string>(type: "character varying(255)", maxLength: 255, nullable: true),
                    description = table.Column<string>(type: "text", nullable: true),
                    creatorid = table.Column<string>(type: "text", nullable: false),
                    privacy = table.Column<bool>(type: "boolean", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("workoutplans_pkey", x => x.planid);
                    table.ForeignKey(
                        name: "workoutplans_creatorid_fkey",
                        column: x => x.creatorid,
                        principalTable: "users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "postimages",
                columns: table => new
                {
                    postimageid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    postid = table.Column<int>(type: "integer", nullable: true),
                    imageid = table.Column<int>(type: "integer", nullable: true),
                    imagelocation = table.Column<string>(type: "text", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("postimages_pkey", x => x.postimageid);
                    table.ForeignKey(
                        name: "postimages_imageid_fkey",
                        column: x => x.imageid,
                        principalTable: "images",
                        principalColumn: "imageid");
                    table.ForeignKey(
                        name: "postimages_postid_fkey",
                        column: x => x.postid,
                        principalTable: "posts",
                        principalColumn: "postid");
                });

            migrationBuilder.CreateTable(
                name: "postssports",
                columns: table => new
                {
                    postsportid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    postid = table.Column<int>(type: "integer", nullable: true),
                    sporttypeid = table.Column<int>(type: "integer", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("postssports_pkey", x => x.postsportid);
                    table.ForeignKey(
                        name: "postssports_postid_fkey",
                        column: x => x.postid,
                        principalTable: "posts",
                        principalColumn: "postid");
                    table.ForeignKey(
                        name: "postssports_sporttypeid_fkey",
                        column: x => x.sporttypeid,
                        principalTable: "sporttype",
                        principalColumn: "sporttypeid");
                });

            migrationBuilder.CreateTable(
                name: "workoutplanexercise",
                columns: table => new
                {
                    workoutplanexerciseid = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    planid = table.Column<int>(type: "integer", nullable: true),
                    exerciseinfoid = table.Column<int>(type: "integer", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("workoutplanexercise_pkey", x => x.workoutplanexerciseid);
                    table.ForeignKey(
                        name: "workoutplanexercise_exerciseinfoid_fkey",
                        column: x => x.exerciseinfoid,
                        principalTable: "exerciseinfo",
                        principalColumn: "exerciseinfoid");
                    table.ForeignKey(
                        name: "workoutplanexercise_planid_fkey",
                        column: x => x.planid,
                        principalTable: "workoutplans",
                        principalColumn: "planid");
                });

            migrationBuilder.CreateIndex(
                name: "IX_chatmessages_chatid",
                table: "chatmessages",
                column: "chatid");

            migrationBuilder.CreateIndex(
                name: "IX_chatmessages_userid",
                table: "chatmessages",
                column: "userid");

            migrationBuilder.CreateIndex(
                name: "IX_chatparticipants_chatid",
                table: "chatparticipants",
                column: "chatid");

            migrationBuilder.CreateIndex(
                name: "IX_chatparticipants_userid",
                table: "chatparticipants",
                column: "userid");

            migrationBuilder.CreateIndex(
                name: "IX_chats_chattypeid",
                table: "chats",
                column: "chattypeid");

            migrationBuilder.CreateIndex(
                name: "IX_comments_postid",
                table: "comments",
                column: "postid");

            migrationBuilder.CreateIndex(
                name: "IX_comments_statusid",
                table: "comments",
                column: "statusid");

            migrationBuilder.CreateIndex(
                name: "IX_comments_userid",
                table: "comments",
                column: "userid");

            migrationBuilder.CreateIndex(
                name: "IX_diaries_creatorid",
                table: "diaries",
                column: "creatorid");

            migrationBuilder.CreateIndex(
                name: "IX_diaryexercise_diaryid",
                table: "diaryexercise",
                column: "diaryid");

            migrationBuilder.CreateIndex(
                name: "IX_diaryexercise_exerciseinfoid",
                table: "diaryexercise",
                column: "exerciseinfoid");

            migrationBuilder.CreateIndex(
                name: "IX_exerciseinfo_exerciseid",
                table: "exerciseinfo",
                column: "exerciseid");

            migrationBuilder.CreateIndex(
                name: "IX_images_postid",
                table: "images",
                column: "postid");

            migrationBuilder.CreateIndex(
                name: "IX_images_userid",
                table: "images",
                column: "userid");

            migrationBuilder.CreateIndex(
                name: "IX_postimages_imageid",
                table: "postimages",
                column: "imageid");

            migrationBuilder.CreateIndex(
                name: "IX_postimages_postid",
                table: "postimages",
                column: "postid");

            migrationBuilder.CreateIndex(
                name: "IX_posts_statusid",
                table: "posts",
                column: "statusid");

            migrationBuilder.CreateIndex(
                name: "IX_posts_titleimageid",
                table: "posts",
                column: "titleimageid");

            migrationBuilder.CreateIndex(
                name: "IX_posts_userid",
                table: "posts",
                column: "userid");

            migrationBuilder.CreateIndex(
                name: "IX_postssports_postid",
                table: "postssports",
                column: "postid");

            migrationBuilder.CreateIndex(
                name: "IX_postssports_sporttypeid",
                table: "postssports",
                column: "sporttypeid");

            migrationBuilder.CreateIndex(
                name: "IX_users_profilepictureid",
                table: "users",
                column: "profilepictureid");

            migrationBuilder.CreateIndex(
                name: "users_email_key",
                table: "users",
                column: "Email",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_usersports_sporttypeid",
                table: "usersports",
                column: "sporttypeid");

            migrationBuilder.CreateIndex(
                name: "IX_usersports_userid",
                table: "usersports",
                column: "userid");

            migrationBuilder.CreateIndex(
                name: "IX_workoutplanexercise_exerciseinfoid",
                table: "workoutplanexercise",
                column: "exerciseinfoid");

            migrationBuilder.CreateIndex(
                name: "IX_workoutplanexercise_planid",
                table: "workoutplanexercise",
                column: "planid");

            migrationBuilder.CreateIndex(
                name: "IX_workoutplans_creatorid",
                table: "workoutplans",
                column: "creatorid");

            migrationBuilder.CreateIndex(
                name: "IX_RoleClaims_RoleId",
                table: "RoleClaims",
                column: "RoleId");

            migrationBuilder.CreateIndex(
                name: "IX_UserClaims_UserId",
                table: "UserClaims",
                column: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_UserLogins_UserId",
                table: "UserLogins",
                column: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_UserRoles_RoleId",
                table: "UserRoles",
                column: "RoleId");

            migrationBuilder.CreateIndex(
                name: "EmailIndex",
                table: "users",
                column: "NormalizedEmail");

            migrationBuilder.AddForeignKey(
                name: "chatmessages_userid_fkey",
                table: "chatmessages",
                column: "userid",
                principalTable: "users",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "chatparticipants_userid_fkey",
                table: "chatparticipants",
                column: "userid",
                principalTable: "users",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "comments_postid_fkey",
                table: "comments",
                column: "postid",
                principalTable: "posts",
                principalColumn: "postid",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "comments_userid_fkey",
                table: "comments",
                column: "userid",
                principalTable: "users",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "diaries_creatorid_fkey",
                table: "diaries",
                column: "creatorid",
                principalTable: "users",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "fk_images_post",
                table: "images",
                column: "postid",
                principalTable: "posts",
                principalColumn: "postid");

            migrationBuilder.AddForeignKey(
                name: "fk_images_user",
                table: "images",
                column: "userid",
                principalTable: "users",
                principalColumn: "Id");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "fk_images_user",
                table: "images");

            migrationBuilder.DropForeignKey(
                name: "posts_userid_fkey",
                table: "posts");

            migrationBuilder.DropForeignKey(
                name: "fk_images_post",
                table: "images");

            migrationBuilder.DropTable(
                name: "chatmessages");

            migrationBuilder.DropTable(
                name: "chatparticipants");

            migrationBuilder.DropTable(
                name: "comments");

            migrationBuilder.DropTable(
                name: "diaryexercise");

            migrationBuilder.DropTable(
                name: "postimages");

            migrationBuilder.DropTable(
                name: "postssports");

            migrationBuilder.DropTable(
                name: "RoleClaims");

            migrationBuilder.DropTable(
                name: "Roles");

            migrationBuilder.DropTable(
                name: "UserClaims");

            migrationBuilder.DropTable(
                name: "UserLogins");

            migrationBuilder.DropTable(
                name: "UserRoles");

            migrationBuilder.DropTable(
                name: "usersports");

            migrationBuilder.DropTable(
                name: "UserTokens");

            migrationBuilder.DropTable(
                name: "workoutplanexercise");

            migrationBuilder.DropTable(
                name: "chats");

            migrationBuilder.DropTable(
                name: "diaries");

            migrationBuilder.DropTable(
                name: "sporttype");

            migrationBuilder.DropTable(
                name: "exerciseinfo");

            migrationBuilder.DropTable(
                name: "workoutplans");

            migrationBuilder.DropTable(
                name: "chattypes");

            migrationBuilder.DropTable(
                name: "exercises");

            migrationBuilder.DropTable(
                name: "users");

            migrationBuilder.DropTable(
                name: "posts");

            migrationBuilder.DropTable(
                name: "status");

            migrationBuilder.DropTable(
                name: "images");
        }
    }
}
