namespace FitHub.Web.Interfaces
{
    public interface IImageService
    {
        Task<int> SaveImageAsync(IFormFile file, string userId);
    }
}
