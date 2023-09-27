using FitHub.Data;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using System.Configuration;

namespace FitHub.Web.Data
{
    public class ApplicationDbContext : IdentityDbContext<ApplicationUser>
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Chat> Chats { get; set; }

        public virtual DbSet<Chatmessage> Chatmessages { get; set; }

        public virtual DbSet<Chatparticipant> Chatparticipants { get; set; }

        public virtual DbSet<Chattype> Chattypes { get; set; }

        public virtual DbSet<Comment> Comments { get; set; }

        public virtual DbSet<Diary> Diaries { get; set; }

        public virtual DbSet<Diaryexercise> Diaryexercises { get; set; }

        public virtual DbSet<Exercise> Exercises { get; set; }

        public virtual DbSet<Exerciseinfo> Exerciseinfos { get; set; }

        public virtual DbSet<Image> Images { get; set; }

        public virtual DbSet<Post> Posts { get; set; }

        public virtual DbSet<Postimage> Postimages { get; set; }

        public virtual DbSet<Postssport> Postssports { get; set; }

        public virtual DbSet<Sporttype> Sporttypes { get; set; }

        public virtual DbSet<Status> Statuses { get; set; }

        public virtual DbSet<ApplicationUser> Users { get; set; }

        public virtual DbSet<Usersport> Usersports { get; set; }

        public virtual DbSet<Workoutplan> Workoutplans { get; set; }

        public virtual DbSet<Workoutplanexercise> Workoutplanexercises { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {            
            modelBuilder.Entity<IdentityUserLogin<string>>()
         .HasKey(l => new { l.LoginProvider, l.ProviderKey });

            modelBuilder.Entity<IdentityUserRole<string>>()
            .HasKey(r => new { r.UserId, r.RoleId });

            modelBuilder.Entity<IdentityUserToken<string>>()
            .HasKey(t => new { t.UserId, t.LoginProvider, t.Name });

            modelBuilder.Entity<Chat>(entity =>
            {
                entity.HasKey(e => e.Chatid).HasName("chats_pkey");

                entity.ToTable("chats");

                entity.Property(e => e.Chatid).HasColumnName("chatid");
                entity.Property(e => e.Chattypeid).HasColumnName("chattypeid");
                entity.Property(e => e.Name)
                    .HasMaxLength(255)
                    .HasColumnName("name");

                entity.HasOne(d => d.Chattype).WithMany(p => p.Chats)
                    .HasForeignKey(d => d.Chattypeid)
                    .HasConstraintName("chats_chattypeid_fkey");
            });

            modelBuilder.Entity<Chatmessage>(entity =>
            {
                entity.HasKey(e => e.Messageid).HasName("chatmessages_pkey");

                entity.ToTable("chatmessages");

                entity.Property(e => e.Messageid).HasColumnName("messageid");
                entity.Property(e => e.Chatid).HasColumnName("chatid");
                entity.Property(e => e.Content).HasColumnName("content");
                entity.Property(e => e.Timestamp)
                    .HasColumnType("timestamp without time zone")
                    .HasColumnName("timestamp");
                entity.Property(e => e.Userid).HasColumnName("userid");

                entity.HasOne(d => d.Chat).WithMany(p => p.Chatmessages)
                    .HasForeignKey(d => d.Chatid)
                    .HasConstraintName("chatmessages_chatid_fkey");

                entity.HasOne(d => d.User).WithMany(p => p.Chatmessages)
                    .HasForeignKey(d => d.Userid)
                    .HasConstraintName("chatmessages_userid_fkey");
            });

            modelBuilder.Entity<Chatparticipant>(entity =>
            {
                entity.HasKey(e => e.Participantid).HasName("chatparticipants_pkey");

                entity.ToTable("chatparticipants");

                entity.Property(e => e.Participantid).HasColumnName("participantid");
                entity.Property(e => e.Chatid).HasColumnName("chatid");
                entity.Property(e => e.Ismoderator).HasColumnName("ismoderator");
                entity.Property(e => e.Userid).HasColumnName("userid");

                entity.HasOne(d => d.Chat).WithMany(p => p.Chatparticipants)
                    .HasForeignKey(d => d.Chatid)
                    .HasConstraintName("chatparticipants_chatid_fkey");

                entity.HasOne(d => d.User).WithMany(p => p.Chatparticipants)
                    .HasForeignKey(d => d.Userid)
                    .HasConstraintName("chatparticipants_userid_fkey");
            });

            modelBuilder.Entity<Chattype>(entity =>
            {
                entity.HasKey(e => e.Chattypeid).HasName("chattypes_pkey");

                entity.ToTable("chattypes");

                entity.Property(e => e.Chattypeid).HasColumnName("chattypeid");
                entity.Property(e => e.Name)
                    .HasMaxLength(255)
                    .HasColumnName("name");
            });

            modelBuilder.Entity<Comment>(entity =>
            {
                entity.HasKey(e => e.Commentid).HasName("comments_pkey");

                entity.ToTable("comments");

                entity.Property(e => e.Commentid).HasColumnName("commentid");
                entity.Property(e => e.Content).HasColumnName("content");
                entity.Property(e => e.Creationdate)
                    .HasColumnType("timestamp without time zone")
                    .HasColumnName("creationdate");
                entity.Property(e => e.Postid).HasColumnName("postid");
                entity.Property(e => e.Statusid).HasColumnName("statusid");
                entity.Property(e => e.Userid).HasColumnName("userid");

                entity.HasOne(d => d.Post).WithMany(p => p.Comments)
                    .HasForeignKey(d => d.Postid)
                    .HasConstraintName("comments_postid_fkey");

                entity.HasOne(d => d.Status).WithMany(p => p.Comments)
                    .HasForeignKey(d => d.Statusid)
                    .HasConstraintName("comments_statusid_fkey");

                entity.HasOne(d => d.User).WithMany(p => p.Comments)
                    .HasForeignKey(d => d.Userid)
                    .HasConstraintName("comments_userid_fkey");
            });

            modelBuilder.Entity<Diary>(entity =>
            {
                entity.HasKey(e => e.Diaryid).HasName("diaries_pkey");

                entity.ToTable("diaries");

                entity.Property(e => e.Diaryid).HasColumnName("diaryid");
                entity.Property(e => e.Creationdate)
                    .HasColumnType("timestamp without time zone")
                    .HasColumnName("creationdate");
                entity.Property(e => e.Creatorid).HasColumnName("creatorid");
                entity.Property(e => e.Lastmodifieddate)
                    .HasColumnType("timestamp without time zone")
                    .HasColumnName("lastmodifieddate");

                entity.HasOne(d => d.Creator).WithMany(p => p.Diaries)
                    .HasForeignKey(d => d.Creatorid)
                    .HasConstraintName("diaries_creatorid_fkey");
            });

            modelBuilder.Entity<Diaryexercise>(entity =>
            {
                entity.HasKey(e => e.Diaryexerciseid).HasName("diaryexercise_pkey");

                entity.ToTable("diaryexercise");

                entity.Property(e => e.Diaryexerciseid).HasColumnName("diaryexerciseid");
                entity.Property(e => e.Diaryid).HasColumnName("diaryid");
                entity.Property(e => e.Exerciseinfoid).HasColumnName("exerciseinfoid");

                entity.HasOne(d => d.Diary).WithMany(p => p.Diaryexercises)
                    .HasForeignKey(d => d.Diaryid)
                    .HasConstraintName("diaryexercise_diaryid_fkey");

                entity.HasOne(d => d.Exerciseinfo).WithMany(p => p.Diaryexercises)
                    .HasForeignKey(d => d.Exerciseinfoid)
                    .HasConstraintName("diaryexercise_exerciseinfoid_fkey");
            });

            modelBuilder.Entity<Exercise>(entity =>
            {
                entity.HasKey(e => e.Exerciseid).HasName("exercises_pkey");

                entity.ToTable("exercises");

                entity.Property(e => e.Exerciseid).HasColumnName("exerciseid");
                entity.Property(e => e.Name)
                    .HasMaxLength(255)
                    .HasColumnName("name");
            });

            modelBuilder.Entity<Exerciseinfo>(entity =>
            {
                entity.HasKey(e => e.Exerciseinfoid).HasName("exerciseinfo_pkey");

                entity.ToTable("exerciseinfo");

                entity.Property(e => e.Exerciseinfoid).HasColumnName("exerciseinfoid");
                entity.Property(e => e.Exerciseid).HasColumnName("exerciseid");
                entity.Property(e => e.Leadtime).HasColumnName("leadtime");
                entity.Property(e => e.Reps).HasColumnName("reps");
                entity.Property(e => e.Sets).HasColumnName("sets");
                entity.Property(e => e.Weightload).HasColumnName("weightload");

                entity.HasOne(d => d.Exercise).WithMany(p => p.Exerciseinfos)
                    .HasForeignKey(d => d.Exerciseid)
                    .HasConstraintName("exerciseinfo_exerciseid_fkey");
            });

            modelBuilder.Entity<Image>(entity =>
            {
                entity.HasKey(e => e.Imageid).HasName("images_pkey");

                entity.ToTable("images");

                entity.Property(e => e.Imageid).HasColumnName("imageid");
                entity.Property(e => e.Creationdate)
                    .HasColumnType("timestamp without time zone")
                    .HasColumnName("creationdate");
                entity.Property(e => e.Imagedata).HasColumnName("imagedata");
                entity.Property(e => e.Imagetype)
                    .HasMaxLength(255)
                    .HasColumnName("imagetype");
                entity.Property(e => e.Postid).HasColumnName("postid");
                entity.Property(e => e.Userid).HasColumnName("userid");

                entity.HasOne(d => d.Post).WithMany(p => p.Images)
                    .HasForeignKey(d => d.Postid)
                    .HasConstraintName("fk_images_post");

                entity.HasOne(d => d.User).WithMany(p => p.Images)
                    .HasForeignKey(d => d.Userid)
                    .HasConstraintName("fk_images_user");
            });

            modelBuilder.Entity<Post>(entity =>
            {
                entity.HasKey(e => e.Postid).HasName("posts_pkey");

                entity.ToTable("posts");

                entity.Property(e => e.Postid).HasColumnName("postid");
                entity.Property(e => e.Content).HasColumnName("content");
                entity.Property(e => e.Creationdate)
                    .HasColumnType("timestamp without time zone")
                    .HasColumnName("creationdate");
                entity.Property(e => e.Statusid).HasColumnName("statusid");
                entity.Property(e => e.Title)
                    .HasMaxLength(255)
                    .HasColumnName("title");
                entity.Property(e => e.Titleimageid).HasColumnName("titleimageid");
                entity.Property(e => e.Userid).HasColumnName("userid");

                entity.HasOne(d => d.Status).WithMany(p => p.Posts)
                    .HasForeignKey(d => d.Statusid)
                    .HasConstraintName("posts_statusid_fkey");

                entity.HasOne(d => d.Titleimage).WithMany(p => p.Posts)
                    .HasForeignKey(d => d.Titleimageid)
                    .HasConstraintName("posts_titleimageid_fkey");

                entity.HasOne(d => d.User).WithMany(p => p.Posts)
                    .HasForeignKey(d => d.Userid)
                    .HasConstraintName("posts_userid_fkey");
            });

            modelBuilder.Entity<Postimage>(entity =>
            {
                entity.HasKey(e => e.Postimageid).HasName("postimages_pkey");

                entity.ToTable("postimages");

                entity.Property(e => e.Postimageid).HasColumnName("postimageid");
                entity.Property(e => e.Imageid).HasColumnName("imageid");
                entity.Property(e => e.Imagelocation).HasColumnName("imagelocation");
                entity.Property(e => e.Postid).HasColumnName("postid");

                entity.HasOne(d => d.Image).WithMany(p => p.Postimages)
                    .HasForeignKey(d => d.Imageid)
                    .HasConstraintName("postimages_imageid_fkey");

                entity.HasOne(d => d.Post).WithMany(p => p.Postimages)
                    .HasForeignKey(d => d.Postid)
                    .HasConstraintName("postimages_postid_fkey");
            });

            modelBuilder.Entity<Postssport>(entity =>
            {
                entity.HasKey(e => e.Postsportid).HasName("postssports_pkey");

                entity.ToTable("postssports");

                entity.Property(e => e.Postsportid).HasColumnName("postsportid");
                entity.Property(e => e.Postid).HasColumnName("postid");
                entity.Property(e => e.Sporttypeid).HasColumnName("sporttypeid");

                entity.HasOne(d => d.Post).WithMany(p => p.Postssports)
                    .HasForeignKey(d => d.Postid)
                    .HasConstraintName("postssports_postid_fkey");

                entity.HasOne(d => d.Sporttype).WithMany(p => p.Postssports)
                    .HasForeignKey(d => d.Sporttypeid)
                    .HasConstraintName("postssports_sporttypeid_fkey");
            });

            modelBuilder.Entity<Sporttype>(entity =>
            {
                entity.HasKey(e => e.Sporttypeid).HasName("sporttype_pkey");

                entity.ToTable("sporttype");

                entity.Property(e => e.Sporttypeid).HasColumnName("sporttypeid");
                entity.Property(e => e.Name)
                    .HasMaxLength(255)
                    .HasColumnName("name");
            });

            modelBuilder.Entity<Status>(entity =>
            {
                entity.HasKey(e => e.Statusid).HasName("status_pkey");

                entity.ToTable("status");

                entity.Property(e => e.Statusid).HasColumnName("statusid");
                entity.Property(e => e.Name)
                    .HasMaxLength(255)
                    .HasColumnName("name");
            });

            modelBuilder.Entity<ApplicationUser>(entity =>
            {
                entity.HasKey(e => e.Id).HasName("users_pkey");

                entity.ToTable("users");

                entity.HasIndex(e => e.Email, "users_email_key").IsUnique();
                entity.Property(e => e.Bio).HasColumnName("bio");
                entity.Property(e => e.Birthdate).HasColumnName("birthdate");
                entity.Property(e => e.Firstname)
                    .HasMaxLength(255)
                    .HasColumnName("firstname");
                entity.Property(e => e.Lastname)
                    .HasMaxLength(255)
                    .HasColumnName("lastname");
                entity.Property(e => e.Location)
                    .HasMaxLength(255)
                    .HasColumnName("location");
                entity.Property(e => e.Profilepictureid).HasColumnName("profilepictureid");
                entity.Property(e => e.Registrationdate)
                    .HasColumnType("timestamp without time zone")
                    .HasColumnName("registrationdate");

                entity.HasOne(d => d.Profilepicture).WithMany(p => p.Users)
                    .HasForeignKey(d => d.Profilepictureid)
                    .HasConstraintName("fk_users_profile_picture");
            });

            modelBuilder.Entity<Usersport>(entity =>
            {
                entity.HasKey(e => e.Usersportid).HasName("usersports_pkey");

                entity.ToTable("usersports");

                entity.Property(e => e.Usersportid).HasColumnName("usersportid");
                entity.Property(e => e.Sporttypeid).HasColumnName("sporttypeid");
                entity.Property(e => e.Userid).HasColumnName("userid");

                entity.HasOne(d => d.Sporttype).WithMany(p => p.Usersports)
                    .HasForeignKey(d => d.Sporttypeid)
                    .HasConstraintName("usersports_sporttypeid_fkey");

                entity.HasOne(d => d.User).WithMany(p => p.Usersports)
                    .HasForeignKey(d => d.Userid)
                    .HasConstraintName("usersports_userid_fkey");
            });

            modelBuilder.Entity<Workoutplan>(entity =>
            {
                entity.HasKey(e => e.Planid).HasName("workoutplans_pkey");

                entity.ToTable("workoutplans");

                entity.Property(e => e.Planid).HasColumnName("planid");
                entity.Property(e => e.Creationdate)
                    .HasColumnType("timestamp without time zone")
                    .HasColumnName("creationdate");
                entity.Property(e => e.Creatorid).HasColumnName("creatorid");
                entity.Property(e => e.Description).HasColumnName("description");
                entity.Property(e => e.Name)
                    .HasMaxLength(255)
                    .HasColumnName("name");
                entity.Property(e => e.Privacy).HasColumnName("privacy");

                entity.HasOne(d => d.Creator).WithMany(p => p.Workoutplans)
                    .HasForeignKey(d => d.Creatorid)
                    .HasConstraintName("workoutplans_creatorid_fkey");
            });

            modelBuilder.Entity<Workoutplanexercise>(entity =>
            {
                entity.HasKey(e => e.Workoutplanexerciseid).HasName("workoutplanexercise_pkey");

                entity.ToTable("workoutplanexercise");

                entity.Property(e => e.Workoutplanexerciseid).HasColumnName("workoutplanexerciseid");
                entity.Property(e => e.Exerciseinfoid).HasColumnName("exerciseinfoid");
                entity.Property(e => e.Planid).HasColumnName("planid");

                entity.HasOne(d => d.Exerciseinfo).WithMany(p => p.Workoutplanexercises)
                    .HasForeignKey(d => d.Exerciseinfoid)
                    .HasConstraintName("workoutplanexercise_exerciseinfoid_fkey");

                entity.HasOne(d => d.Plan).WithMany(p => p.Workoutplanexercises)
                    .HasForeignKey(d => d.Planid)
                    .HasConstraintName("workoutplanexercise_planid_fkey");
            });
        }
    }
}