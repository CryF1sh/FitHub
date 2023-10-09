namespace FitHub.Web.Modeles.PostsModels
{
    public class PostListItem
    {
        public int PostId { get; set; }
        public string Title { get; set; }
        public string CreatorFirstName { get; set; }
        public string CreatorLastName { get; set; }
        public string CreationDate { get; set; }
        public int? TitleImageId { get; set; }
    }
}
