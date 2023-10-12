using FitHub.Web.Interfaces;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;

namespace FitHub.Web.Controllers
{
    [Route("api/images")]
    [ApiController]
    [Authorize]
    public class ImageController : ControllerBase
    {
        private readonly IImageService _imageService;
        private readonly ILogger<ImageController> _logger;

        public ImageController(IImageService imageService, ILogger<ImageController> logger)
        {
            _imageService = imageService;
            _logger = logger;
        }

        [HttpPost("upload")]
        public async Task<IActionResult> UploadImage([FromForm] IFormFile file)
        {
            if (file == null || file.Length == 0)
            {
                _logger.LogError("Неопределённый файл");
                return BadRequest("Invalid file");
            }

            try
            {
                var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
                var imageId = await _imageService.SaveImageAsync(file, userId);
                _logger.LogInformation($"Добавлено изоображение {imageId} пользователем с id:{userId}");
                return Ok(new { ImageId = imageId });
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Неизвестная ошибка сервера");
                return StatusCode(500, $"Internal server error: {ex.Message}");
            }
        }
    }

}
