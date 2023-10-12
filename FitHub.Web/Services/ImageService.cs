using FitHub.Data;
using FitHub.Web.Data;
using FitHub.Web.Interfaces;

namespace FitHub.Web.Services
{
    public class ImageService : IImageService
    {
        private readonly ApplicationDbContext _context;

        public ImageService(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<int> SaveImageAsync(IFormFile file, string userId)
        {
            // Логика сохранения изображения в базе данных
            // тут можно добавить проверку изоображения или его сжатие(если делать на сервре)

            var imageData = await ReadFileAsync(file);
            var image = new Image
            {
                Imagedata = imageData,
                Imagetype = file.ContentType,
                Creationdate = DateTime.Now,
                Userid = userId
            };

            _context.Images.Add(image);
            await _context.SaveChangesAsync();

            return image.Imageid;
        }

        private async Task<byte[]> ReadFileAsync(IFormFile file)
        {
            using (var stream = new MemoryStream())
            {
                await file.CopyToAsync(stream);
                return stream.ToArray();
            }
        }
    }
}
